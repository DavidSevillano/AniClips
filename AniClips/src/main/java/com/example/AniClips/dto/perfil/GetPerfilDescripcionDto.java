package com.example.AniClips.dto.perfil;

import com.example.AniClips.model.Perfil;

public record GetPerfilDescripcionDto(
        String descripcion
) {

    public static GetPerfilDescripcionDto of (Perfil perfil){
        return new GetPerfilDescripcionDto(
                perfil.getDescripcion()
        );
    }

}
