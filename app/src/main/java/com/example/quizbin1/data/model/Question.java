package com.example.quizbin1.data.model;

import java.util.UUID;

public class Question {
    private UUID questionId;
    private UUID semesterId;
    private String content;

    public Question() {
    }

    public Question(UUID questionId, UUID semesterId, String content) {
        this.questionId = questionId;
        this.semesterId = semesterId;
        this.content = content;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public UUID getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(UUID semesterId) {
        this.semesterId = semesterId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
