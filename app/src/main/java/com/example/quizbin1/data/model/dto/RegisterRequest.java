package com.example.quizbin1.data.model.dto;

public class RegisterRequest {
    private String username;
    private String passwordHash;
    private int roleId;

    public RegisterRequest(String username, String passwordHash, int roleId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    // Getter & Setter
}
