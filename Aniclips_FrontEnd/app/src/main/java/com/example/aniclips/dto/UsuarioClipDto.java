package com.example.aniclips.dto;

import com.google.gson.annotations.SerializedName;

public class UsuarioClipDto {
    @SerializedName("Username")
    private String username;

    @SerializedName("getPerfilAvatarDto")
    private PerfilAvatarDto getPerfilAvatarDto;

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PerfilAvatarDto getGetPerfilAvatarDto() {
        return getPerfilAvatarDto;
    }

    public void setGetPerfilAvatarDto(PerfilAvatarDto getPerfilAvatarDto) {
        this.getPerfilAvatarDto = getPerfilAvatarDto;
    }
}