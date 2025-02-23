package com.example.AniClips.security.user.dto.signupLogin;

public record CreateUserRequest(
        String username, String email, String password, String verifyPassword
) {
}
