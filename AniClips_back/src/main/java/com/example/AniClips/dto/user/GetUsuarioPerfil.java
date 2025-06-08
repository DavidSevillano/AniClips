package com.example.AniClips.dto.user;

import com.example.AniClips.dto.perfil.GetPerfilDto;

public record GetUsuarioPerfil(
        String username,
        GetPerfilDto getPerfilDto
) {
}
