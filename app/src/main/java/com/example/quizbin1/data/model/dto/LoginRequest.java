package com.example.quizbin1.data.model.dto;

public class LoginRequest {
    private String username;
    private String passwordHash;

    public LoginRequest(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    // Getter & Setter (tùy IDE)
}
