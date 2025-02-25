package com.example.AniClips.dto.clip;

import com.example.AniClips.model.Clip;

public record GetClipMiniaturaDto(
    int duracion,
    String miniatura,
    String nombreAnime
) {
        public static GetClipMiniaturaDto of (Clip clip) {
            return new GetClipMiniaturaDto(
                    clip.getDuracion(),
                    clip.getMiniatura(),
                    clip.getNombreAnime()
            );
        }
    }
