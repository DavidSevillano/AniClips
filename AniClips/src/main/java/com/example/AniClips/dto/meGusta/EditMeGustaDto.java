package com.example.AniClips.dto.meGusta;

import java.util.UUID;

public record EditMeGustaDto(
        UUID usuarioId,
        Long clipId
) {
}
