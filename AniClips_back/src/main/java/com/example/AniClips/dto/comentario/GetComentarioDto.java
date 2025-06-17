package com.example.AniClips.dto.comentario;

import com.example.AniClips.model.Comentario;
import com.example.AniClips.dto.user.GetUsuarioClipDto;

import java.time.LocalDate;

public record GetComentarioDto(
        Long id,
    String texto,
    LocalDate fecha,
    GetUsuarioClipDto getUsuarioClipDto
) {

        public static GetComentarioDto of (Comentario comentario) {

            return new GetComentarioDto(
                    comentario.getId(),
                    comentario.getTexto(),
                    comentario.getFecha(),
                    GetUsuarioClipDto.of(comentario.getUsuario())
            );
        }
    }
