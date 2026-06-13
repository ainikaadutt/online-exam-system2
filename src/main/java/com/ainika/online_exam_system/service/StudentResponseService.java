package com.ainika.online_exam_system.service;
import com.ainika.online_exam_system.dto.ResultDTO;
import com.ainika.online_exam_system.entity.StudentResponse;
import com.ainika.online_exam_system.repository.StudentResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ainika.online_exam_system.entity.Question;
import com.ainika.online_exam_system.repository.QuestionRepository;
import java.util.List;

@Service
public class StudentResponseService {

    @Autowired
    private StudentResponseRepository studentResponseRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public StudentResponse saveResponse(StudentResponse response) {

        Long questionId = response.getQuestion().getId();

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        if (response.getSelectedAnswer()
                .equalsIgnoreCase(question.getCorrectAnswer())) {

            response.setMarks(1);

        } else {

            response.setMarks(0);
        }

        return studentResponseRepository.save(response);
    }

    public ResultDTO getStudentScore(Long studentId) {

        List<StudentResponse> responses =
                studentResponseRepository.findByStudentId(studentId);

        int totalMarks = 0;

        for (StudentResponse response : responses) {

            if (response.getMarks() != null) {
                totalMarks += response.getMarks();
            }
        }

        ResultDTO resultDTO = new ResultDTO();

        resultDTO.setStudentId(studentId);
        resultDTO.setTotalMarks(totalMarks);

        return resultDTO;
    }

    public List<StudentResponse> getAllResponses() {
        return studentResponseRepository.findAll();
    }
}