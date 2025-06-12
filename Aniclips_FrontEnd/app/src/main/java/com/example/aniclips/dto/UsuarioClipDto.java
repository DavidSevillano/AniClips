package com.example.aniclips.dto;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UsuarioClipDto {
    @SerializedName("idUser")
    private UUID idUser;

    @SerializedName("username")
    private String username;

    @SerializedName("getPerfilAvatarDto")
    private PerfilAvatarDto getPerfilAvatarDto;

    // Getters y Setters
    public UUID getIdUser() {
        return idUser;
    }

    public void setIdUser(UUID id) {
        this.idUser = id;
    }

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