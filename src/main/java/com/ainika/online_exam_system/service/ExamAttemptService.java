package com.ainika.online_exam_system.service;

import com.ainika.online_exam_system.dto.ExamSubmissionRequest;
import com.ainika.online_exam_system.entity.Exam;
import com.ainika.online_exam_system.entity.ExamAttempt;
import com.ainika.online_exam_system.entity.Question;
import com.ainika.online_exam_system.entity.StudentResponse;
import com.ainika.online_exam_system.entity.User;
import com.ainika.online_exam_system.repository.ExamAttemptRepository;
import com.ainika.online_exam_system.repository.ExamRepository;
import com.ainika.online_exam_system.repository.QuestionRepository;
import com.ainika.online_exam_system.repository.StudentResponseRepository;
import com.ainika.online_exam_system.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExamAttemptService {

    @Autowired
    private ExamAttemptRepository examAttemptRepository;

    @Autowired
    private StudentResponseRepository studentResponseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamRepository examRepository;


    // Submit complete exam
    public ExamAttempt submitExam(
            ExamSubmissionRequest request,
            String studentEmail) {

        User student = userRepository.findByEmail(studentEmail)
                .orElseThrow(() ->
                        new RuntimeException("Student not found")
                );

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() ->
                        new RuntimeException("Exam not found")
                );

        /*
         * Create the attempt first.
         */
        ExamAttempt attempt = new ExamAttempt();

        attempt.setStudent(student);
        attempt.setExam(exam);
        attempt.setScore(0);
        attempt.setSubmitted(true);

        attempt = examAttemptRepository.save(attempt);

        int score = 0;

        /*
         * Save each student response and connect it
         * to this particular attempt.
         */
        for (Map.Entry<Long, String> entry :
                request.getAnswers().entrySet()) {

            Long questionId = entry.getKey();

            String selectedAnswer = entry.getValue();

            Question question =
                    questionRepository.findById(questionId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Question not found"
                                    )
                            );

            /*
             * Make sure the question belongs
             * to the submitted exam.
             */
            if (!question.getExam().getId()
                    .equals(request.getExamId())) {

                throw new RuntimeException(
                        "Question does not belong to this exam"
                );
            }

            StudentResponse response =
                    new StudentResponse();

            response.setStudent(student);
            response.setQuestion(question);
            response.setAttempt(attempt);
            response.setSelectedAnswer(selectedAnswer);

            /*
             * Check answer.
             */
            if (selectedAnswer != null &&
                    selectedAnswer.equalsIgnoreCase(
                            question.getCorrectAnswer()
                    )) {

                response.setMarks(1);
                score++;

            } else {

                response.setMarks(0);
            }

            studentResponseRepository.save(response);
        }

        /*
         * Update final score.
         */
        attempt.setScore(score);

        return examAttemptRepository.save(attempt);
    }


    // Existing manual attempt creation
    public ExamAttempt saveAttempt(ExamAttempt attempt) {

        Long studentId = attempt.getStudent().getId();

        Integer score =
                studentResponseRepository
                        .countByStudentIdAndMarks(studentId, 1);

        attempt.setScore(score);

        return examAttemptRepository.save(attempt);
    }


    // Get all attempts
    public List<ExamAttempt> getAllAttempts() {

        return examAttemptRepository.findAll();
    }


    // Get student's attempts
    public List<ExamAttempt> getStudentAttempts(
            String studentEmail) {

        User student =
                userRepository.findByEmail(studentEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student not found"
                                )
                        );

        return examAttemptRepository
                .findByStudentId(student.getId());
    }


    // Get specific attempt
    public ExamAttempt getAttemptById(Long id) {

        return examAttemptRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam attempt not found"
                        )
                );
    }


    // Get responses belonging to a specific attempt
    public List<StudentResponse> getAttemptResponses(Long attemptId) {

        /*
         * Make sure the attempt exists.
         */
        examAttemptRepository.findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Exam attempt not found"
                        )
                );

        return studentResponseRepository
                .findByAttemptId(attemptId);
    }
}