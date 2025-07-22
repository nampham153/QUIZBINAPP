package com.example.quizbin1.data.model.dto;

public class AddOptionRequestDTO {
    private String questionId;
    private String content;
    private Boolean isCorrect;

    public AddOptionRequestDTO(String questionId, String content, Boolean isCorrect) {
        this.questionId = questionId;
        this.content = content;
        this.isCorrect = isCorrect;
    }

}