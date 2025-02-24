package com.example.AniClips.dto.clip;

import com.example.AniClips.validation.clip.ValidMiniaturaExtension;
import com.example.AniClips.validation.clip.ValidVideoExtension;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record EditClipDto (
        @ValidVideoExtension
        String urlClip,

        @NotBlank(message = "El nombre del anime no puede estar vacio")
        String nombreAnime,

        @NotBlank(message = "El género no puede estar vacío")
        String genero,

        @ValidMiniaturaExtension
        String urlMiniatura,

        String descripcion
){
}
