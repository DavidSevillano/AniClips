package com.example.AniClips.dto.perfil;

import java.util.UUID;

public record EditPerfilDescripcionDto(
        UUID usuarioId,
        String descripcion
) {
}
