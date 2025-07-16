package com.example.quizbin1.data.model.dto;

public class LoginRequest {
    private String username;
    private String passwordHash;

    public LoginRequest() {
    }

    public LoginRequest(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
