package com.example.AniClips.security.user.dto;

public record CreateUserRequest(String username, String password, String verifyPassword) {
    public CreateUserRequest(String username, String password, String verifyPassword) {
        this.username = username;
        this.password = password;
        this.verifyPassword = verifyPassword;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }

    public String verifyPassword() {
        return this.verifyPassword;
    }
}
