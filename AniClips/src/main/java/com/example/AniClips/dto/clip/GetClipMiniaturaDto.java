package com.example.AniClips.dto.clip;

import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Valoracion;
import com.example.AniClips.security.user.dto.GetUsuarioClipDto;

import java.time.LocalDate;

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
