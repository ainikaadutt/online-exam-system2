package com.ainika.online_exam_system.repository;

import com.ainika.online_exam_system.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {

}