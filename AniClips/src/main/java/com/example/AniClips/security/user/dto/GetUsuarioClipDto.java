package com.example.AniClips.security.user.dto;

import com.example.AniClips.dto.perfil.GetPerfilAvatarDto;
import com.example.AniClips.security.user.model.Usuario;

public record GetUsuarioClipDto(
        String Username,
        GetPerfilAvatarDto getPerfilAvatarDto
) {

    public static  GetUsuarioClipDto of (Usuario usuario){
        return new GetUsuarioClipDto(
                usuario.getUsername(),
                GetPerfilAvatarDto.of(usuario.getPerfil())
        );
    }
}
