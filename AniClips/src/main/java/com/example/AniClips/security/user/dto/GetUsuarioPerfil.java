package com.example.AniClips.security.user.dto;

import com.example.AniClips.dto.perfil.GetPerfilDto;

public record GetUsuarioPerfil(
        String username,
        GetPerfilDto getPerfilDto
) {
}
