package com.example.quizbin1.data.model.dto;

import java.util.UUID;

public class OptionDTO {
    private UUID optionId;
    private UUID questionId;
    private String content;
    private boolean isCorrect;

    public OptionDTO() {
    }

    public OptionDTO(UUID optionId, UUID questionId, String content, boolean isCorrect) {
        this.optionId = optionId;
        this.questionId = questionId;
        this.content = content;
        this.isCorrect = isCorrect;
    }

    public UUID getOptionId() {
        return optionId;
    }

    public void setOptionId(UUID optionId) {
        this.optionId = optionId;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
