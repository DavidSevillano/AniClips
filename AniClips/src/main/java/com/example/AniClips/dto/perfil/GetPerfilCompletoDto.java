package com.example.AniClips.dto.perfil;

import com.example.AniClips.dto.clip.GetClipMiniaturaDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Perfil;

import java.util.List;

public record GetPerfilCompletoDto(
        String username,
        String foto,
        int numeroClips,
        int numeroSeguidores,
        int numeroSeguidos,
        String descripcion,
        List<GetClipMiniaturaDto> clips
) {

    public static GetPerfilCompletoDto of (Perfil perfil){
        return new GetPerfilCompletoDto(
                perfil.getUsuario().getUsername(),
                perfil.getAvatar(),
                perfil.getUsuario().getClips().size(),
                perfil.getUsuario().getSeguidores().size(),
                perfil.getUsuario().getSeguidos().size(),
                perfil.getDescripcion(),
                perfil.getUsuario().getClips().stream()
                        .map(GetClipMiniaturaDto::of)
                        .toList()
        );
    }
}
