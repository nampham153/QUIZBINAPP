package com.example.quizbin1.data.model.dto;

public class AddQuestionRequestDTO {
    private String semesterId;
    private String content;

    public AddQuestionRequestDTO(String semesterId, String content) {
        this.semesterId = semesterId;
        this.content = content;
    }

}
