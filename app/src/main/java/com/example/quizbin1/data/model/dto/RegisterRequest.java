package com.example.quizbin1.data.model.dto;

import java.util.UUID;

public class RegisterRequest {
    private String username;
    private String passwordHash;
    private UUID roleId;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String passwordHash, UUID roleId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
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

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}
