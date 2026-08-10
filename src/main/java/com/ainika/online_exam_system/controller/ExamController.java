package com.ainika.online_exam_system.controller;

import com.ainika.online_exam_system.entity.Exam;
import com.ainika.online_exam_system.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping
    public Exam createExam(@RequestBody Exam exam) {
        return examService.saveExam(exam);
    }

    @GetMapping
    public List<Exam> getAllExams() {
        return examService.getAllExams();
    }

    // Get Exam By ID
    @GetMapping("/{id}")
    public Exam getExamById(@PathVariable Long id) {
        return examService.getExamById(id);
    }

    // Update Exam
    @PutMapping("/{id}")
    public Exam updateExam(@PathVariable Long id,
                           @RequestBody Exam exam) {
        return examService.updateExam(id, exam);
    }

    // Delete Exam
    @DeleteMapping("/{id}")
    public void deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
    }
}
