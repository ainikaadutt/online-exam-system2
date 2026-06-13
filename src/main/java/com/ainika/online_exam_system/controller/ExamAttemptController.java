package com.ainika.online_exam_system.controller;

import com.ainika.online_exam_system.entity.ExamAttempt;
import com.ainika.online_exam_system.service.ExamAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attempts")
public class ExamAttemptController {

    @Autowired
    private ExamAttemptService examAttemptService;

    @PostMapping
    public ExamAttempt createAttempt(@RequestBody ExamAttempt attempt) {
        return examAttemptService.saveAttempt(attempt);
    }

    @GetMapping
    public List<ExamAttempt> getAllAttempts() {
        return examAttemptService.getAllAttempts();
    }
}