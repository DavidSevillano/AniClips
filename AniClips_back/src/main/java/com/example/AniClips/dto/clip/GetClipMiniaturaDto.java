package com.example.AniClips.dto.clip;

import com.example.AniClips.model.Clip;

public record GetClipMiniaturaDto(
        Long id,
        int duracion,
        String miniatura,
        String nombreAnime
) {
    public static GetClipMiniaturaDto of(Clip clip) {
        return new GetClipMiniaturaDto(
                clip.getId(),
                clip.getDuracion(),
                clip.getMiniatura(),
                clip.getNombreAnime()
        );
    }
}
