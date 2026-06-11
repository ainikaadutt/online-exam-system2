package com.ainika.online_exam_system.controller;

import com.ainika.online_exam_system.entity.Question;
import com.ainika.online_exam_system.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/exam/{examId}")
    public List<Question> getQuestionsByExamId(@PathVariable Long examId) {
        return questionService.getQuestionsByExamId(examId);
    }
}
