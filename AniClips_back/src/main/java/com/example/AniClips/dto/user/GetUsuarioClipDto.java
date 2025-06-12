package com.example.AniClips.dto.user;

import com.example.AniClips.dto.perfil.GetPerfilAvatarDto;
import com.example.AniClips.model.Usuario;

import java.util.UUID;

public record GetUsuarioClipDto(
        UUID idUser,
        String username,
        GetPerfilAvatarDto getPerfilAvatarDto
) {

    public static  GetUsuarioClipDto of (Usuario usuario){
        return new GetUsuarioClipDto(
                usuario.getId(),
                usuario.getUsername(),
                GetPerfilAvatarDto.of(usuario.getPerfil())
        );
    }
}
