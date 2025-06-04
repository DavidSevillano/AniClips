package com.example.aniclips.dto;

public class UsuarioClipDto {
    private String username;
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
