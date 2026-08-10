package com.ainika.online_exam_system.controller;

import com.ainika.online_exam_system.dto.ExamSubmissionRequest;
import com.ainika.online_exam_system.entity.ExamAttempt;
import com.ainika.online_exam_system.entity.StudentResponse;
import com.ainika.online_exam_system.service.ExamAttemptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attempts")
public class ExamAttemptController {

    @Autowired
    private ExamAttemptService examAttemptService;


    // Create attempt
    @PostMapping
    public ExamAttempt createAttempt(
            @RequestBody ExamAttempt attempt) {

        return examAttemptService.saveAttempt(attempt);
    }


    // Submit complete exam
    @PostMapping("/submit")
    public ExamAttempt submitExam(
            @RequestBody ExamSubmissionRequest request,
            Authentication authentication) {

        String studentEmail =
                authentication.getName();

        return examAttemptService.submitExam(
                request,
                studentEmail
        );
    }


    // Get student's attempts
    @GetMapping("/student")
    public List<ExamAttempt> getStudentAttempts(
            Authentication authentication) {

        String studentEmail =
                authentication.getName();

        return examAttemptService
                .getStudentAttempts(studentEmail);
    }


    // Get a specific attempt
    @GetMapping("/{id}")
    public ExamAttempt getAttempt(
            @PathVariable Long id) {

        return examAttemptService
                .getAttemptById(id);
    }


    // Get responses for a specific attempt
    @GetMapping("/{id}/responses")
    public List<StudentResponse> getAttemptResponses(
            @PathVariable Long id) {

        return examAttemptService
                .getAttemptResponses(id);
    }


    // Get all attempts
    @GetMapping
    public List<ExamAttempt> getAllAttempts() {

        return examAttemptService
                .getAllAttempts();
    }
}