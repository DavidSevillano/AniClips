package com.example.AniClips.dto.comentario;

import jakarta.validation.constraints.NotNull;

public record EditComentarioDto (
        Long clipId,

        @NotNull(message = "{error.texto.notNull}")
        String texto
){
}
