package com.example.AniClips.security.user.dto;

import com.example.AniClips.security.user.model.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.UUID;

public record UserResponse(UUID id, String username, String token, String refreshToken) {
    public UserResponse(UUID id, String username, @JsonInclude(Include.NON_NULL) String token, @JsonInclude(Include.NON_NULL) String refreshToken) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public static UserResponse of(Usuario usuario) {
        return new UserResponse(usuario.getId(), usuario.getUsername(), (String)null, (String)null);
    }

    public static UserResponse of(Usuario usuario, String token, String refreshToken) {
        return new UserResponse(usuario.getId(), usuario.getUsername(), token, refreshToken);
    }

    public UUID id() {
        return this.id;
    }

    public String username() {
        return this.username;
    }

    @JsonInclude(Include.NON_NULL)
    public String token() {
        return this.token;
    }

    @JsonInclude(Include.NON_NULL)
    public String refreshToken() {
        return this.refreshToken;
    }
}
