package com.example.AniClips.dto.user.signupLogin;

import com.example.AniClips.model.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public record UserResponse(
        UUID id,
        String username,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        Set<String> roles,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String refreshToken
) {

    public static UserResponse of(Usuario user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()),
                null,
                null
        );
    }

    public static UserResponse of(Usuario user, String token, String refreshToken) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRoles().stream().map(Enum::name).collect(Collectors.toSet()),
                token,
                refreshToken
        );
    }
}
