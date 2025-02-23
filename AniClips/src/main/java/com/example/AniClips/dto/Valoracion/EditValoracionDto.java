package com.example.AniClips.dto.Valoracion;

import java.util.UUID;

public record EditValoracionDto(
        UUID usuarioId,
        Long clipId,
        double puntuacion
) {
}
