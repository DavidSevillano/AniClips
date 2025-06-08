package com.example.AniClips.dto.Valoracion;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditValoracionDto(
        Long clipId,

        @NotNull(message = "{error.valoracion.notNull}")
        double puntuacion
) {
}
