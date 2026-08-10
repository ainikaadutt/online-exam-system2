package com.ainika.online_exam_system.service;

import com.ainika.online_exam_system.entity.Question;
import com.ainika.online_exam_system.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // Create Question
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Get All Questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Get Questions by Exam ID
    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    // Get Question by ID
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    // Update Question
    public Question updateQuestion(Long id, Question updatedQuestion) {

        Question question = getQuestionById(id);

        question.setQuestionText(updatedQuestion.getQuestionText());
        question.setOptionA(updatedQuestion.getOptionA());
        question.setOptionB(updatedQuestion.getOptionB());
        question.setOptionC(updatedQuestion.getOptionC());
        question.setOptionD(updatedQuestion.getOptionD());
        question.setCorrectAnswer(updatedQuestion.getCorrectAnswer());

        // Keep the same exam association
        question.setExam(updatedQuestion.getExam());

        return questionRepository.save(question);
    }

    // Delete Question
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}