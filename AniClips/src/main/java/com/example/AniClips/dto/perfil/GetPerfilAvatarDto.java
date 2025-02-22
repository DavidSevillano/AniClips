package com.example.AniClips.dto.perfil;

import com.example.AniClips.model.Perfil;

public record GetPerfilAvatarDto(
        String avatar
) {

    public static GetPerfilAvatarDto of (Perfil perfil){
        return new GetPerfilAvatarDto(
                perfil.getAvatar()
        );
    }

}
