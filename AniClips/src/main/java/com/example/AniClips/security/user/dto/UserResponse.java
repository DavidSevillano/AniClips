package com.example.AniClips.security.user.dto;

import com.example.AniClips.security.user.model.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String refreshToken

) {

    public static UserResponse of (Usuario user) {
        return new UserResponse(user.getId(), user.getUsername(), null, null);
    }

    public static UserResponse of (Usuario user, String token, String refreshToken) {
        return new UserResponse(user.getId(), user.getUsername(), token, refreshToken);
    }

}