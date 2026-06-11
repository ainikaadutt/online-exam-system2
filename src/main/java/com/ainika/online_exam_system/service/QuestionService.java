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

    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }
}