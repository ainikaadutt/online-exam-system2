package com.ainika.online_exam_system.dto;

import java.util.Map;

public class ExamSubmissionRequest {

    private Long examId;

    private Map<Long, String> answers;


    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }


    public Map<Long, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, String> answers) {
        this.answers = answers;
    }
}