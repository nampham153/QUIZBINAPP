package com.example.quizbin1.data.model.dto;

import java.util.UUID;

public class AddSemesterRequestDTO {
    private UUID subjectId;
    private String semesterName;

    public UUID getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(UUID subjectId) {
        this.subjectId = subjectId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }
}
