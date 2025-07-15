package com.example.quizbin1.data.model.dto;

public class LoginRequest {
    private String username;
    private String passwordHash;

    public LoginRequest(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public LoginRequest() {} // constructor rỗng

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
