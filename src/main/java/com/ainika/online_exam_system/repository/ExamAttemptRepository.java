package com.ainika.online_exam_system.repository;

import com.ainika.online_exam_system.entity.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamAttemptRepository
        extends JpaRepository<ExamAttempt, Long> {

    List<ExamAttempt> findByStudentId(Long studentId);

}
