package com.ainika.online_exam_system.dto;
import com.ainika.online_exam_system.dto.ResultDTO;

public class ResultDTO {

    private Long studentId;
    private Integer totalMarks;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }
}

