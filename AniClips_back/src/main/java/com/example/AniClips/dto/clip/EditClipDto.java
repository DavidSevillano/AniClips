package com.example.AniClips.dto.clip;

import com.example.AniClips.validation.clip.ValidMiniaturaExtension;
import com.example.AniClips.validation.clip.ValidVideoExtension;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record EditClipDto (
        @NotBlank(message = "{error.nombreAnime.notBlank}")
        String nombreAnime,

        @NotBlank(message = "{error.genero.notBlank}")
        String genero,

        String descripcion,

        @NotNull(message = "{error.video.notNull}")
        MultipartFile video,

        @NotNull(message = "{error.miniatura.notNull}")
        MultipartFile miniatura
) {
}

