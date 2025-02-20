package com.example.AniClips.security.user.dto;

public record LoginRequest(String username, String password) {
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username() {
        return this.username;
    }

    public String password() {
        return this.password;
    }
}
