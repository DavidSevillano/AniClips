package com.example.AniClips.dto.perfil;

import com.example.AniClips.dto.usuario.GetUsuarioClipDto;
import com.example.AniClips.model.Perfil;
import com.example.AniClips.security.user.model.Usuario;

public record GetPerfilAvatarDto(
        String avatar
) {

    public static GetPerfilAvatarDto of (Perfil perfil){
        return new GetPerfilAvatarDto(
                perfil.getAvatar()
        );
    }

}
