package com.example.quizbin1.data.model;

import java.util.UUID;

public class User {
    private UUID userId;
    private String username;
    private String passwordHash;
    private UUID roleId;

    public User() {
    }

    public User(UUID userId, String username, String passwordHash, UUID roleId) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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
