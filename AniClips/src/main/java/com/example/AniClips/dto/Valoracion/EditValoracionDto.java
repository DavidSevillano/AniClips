package com.example.AniClips.dto.Valoracion;

import java.util.UUID;

public record EditValoracionDto(
        Long clipId,
        double puntuacion
) {
}
