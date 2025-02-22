package com.example.AniClips.dto.comentario;

import java.util.UUID;

public record EditComentarioDto(
        UUID usuarioId,
        Long clipId,
        String texto
) {
}
