package com.example.AniClips.dto.clip;

import com.example.AniClips.validation.clip.ValidMiniaturaExtension;
import com.example.AniClips.validation.clip.ValidVideoExtension;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record EditClipDto (
        @NotBlank(message = "El nombre del anime no puede estar vacío")
        String nombreAnime,

        @NotBlank(message = "El género no puede estar vacío")
        String genero,

        String descripcion,

        @NotNull(message = "El video no puede estar vacío")
        MultipartFile video,

        @NotNull(message = "La miniatura no puede estar vacía")
        MultipartFile miniatura
) {

}

