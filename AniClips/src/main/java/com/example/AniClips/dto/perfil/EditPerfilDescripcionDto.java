package com.example.AniClips.dto.perfil;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditPerfilDescripcionDto(

        @NotNull(message = "{error.descripcion.notNull}")
        String descripcion
) {
}
