package com.ainika.online_exam_system.service;

import com.ainika.online_exam_system.entity.ExamAttempt;
import com.ainika.online_exam_system.repository.ExamAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ainika.online_exam_system.repository.StudentResponseRepository;
import java.util.List;

@Service
public class ExamAttemptService {

    @Autowired
    private ExamAttemptRepository examAttemptRepository;

    @Autowired
    private StudentResponseRepository studentResponseRepository;

    public ExamAttempt saveAttempt(ExamAttempt attempt) {

        Long studentId = attempt.getStudent().getId();

        Integer score =
                studentResponseRepository.countByStudentIdAndMarks(studentId, 1);

        attempt.setScore(score);

        return examAttemptRepository.save(attempt);
    }

    public List<ExamAttempt> getAllAttempts() {
        return examAttemptRepository.findAll();
    }
}
