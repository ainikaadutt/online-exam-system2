package com.ainika.online_exam_system.service;

import com.ainika.online_exam_system.entity.Exam;
import com.ainika.online_exam_system.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    // Create Exam
    public Exam saveExam(Exam exam) {
        return examRepository.save(exam);
    }

    // Get All Exams
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    // Get Exam By ID
    public Exam getExamById(Long id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found"));
    }

    // Update Exam
    public Exam updateExam(Long id, Exam updatedExam) {

        Exam exam = getExamById(id);

        exam.setTitle(updatedExam.getTitle());
        exam.setDescription(updatedExam.getDescription());
        exam.setDuration(updatedExam.getDuration());
        exam.setStartTime(updatedExam.getStartTime());
        exam.setEndTime(updatedExam.getEndTime());

        return examRepository.save(exam);
    }

    // Delete Exam
    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }
}