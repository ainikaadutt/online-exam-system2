package com.ainika.online_exam_system.controller;

import com.ainika.online_exam_system.dto.ResultDTO;
import com.ainika.online_exam_system.entity.StudentResponse;
import com.ainika.online_exam_system.service.StudentResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responses")
public class StudentResponseController {

    @Autowired
    private StudentResponseService studentResponseService;

    @PostMapping
    public StudentResponse createResponse(@RequestBody StudentResponse response) {
        return studentResponseService.saveResponse(response);
    }

    @GetMapping
    public List<StudentResponse> getAllResponses() {
        return studentResponseService.getAllResponses();
    }

    @GetMapping("/result/{studentId}")
    public ResultDTO getStudentResult(@PathVariable Long studentId) {
        return studentResponseService.getStudentScore(studentId);
    }
}
