package com.example.AniClips.dto.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record EditPerfilAvatarDto(

        @NotNull(message = "{error.foto.notNull}")
        MultipartFile foto

) {
}
