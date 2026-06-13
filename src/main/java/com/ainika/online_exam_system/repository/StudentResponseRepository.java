package com.ainika.online_exam_system.repository;

import java.util.List;
import com.ainika.online_exam_system.entity.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentResponseRepository extends JpaRepository<StudentResponse, Long> {
    List<StudentResponse> findByStudentId(Long studentId);
    Integer countByStudentIdAndMarks(Long studentId, Integer marks);
}
