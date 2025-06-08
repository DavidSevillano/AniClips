package com.example.AniClips.dto.user;

import com.example.AniClips.dto.perfil.GetPerfilAvatarDto;
import com.example.AniClips.model.Usuario;

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
