package com.example.quizbin1.data.model.dto;

import java.util.UUID;

public class SemesterDTO {
    private UUID semesterId;
    private UUID subjectId;
    private String semesterName;

    public SemesterDTO() {
    }

    public SemesterDTO(UUID semesterId, UUID subjectId, String semesterName) {
        this.semesterId = semesterId;
        this.subjectId = subjectId;
        this.semesterName = semesterName;
    }

    public UUID getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(UUID semesterId) {
        this.semesterId = semesterId;
    }

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
