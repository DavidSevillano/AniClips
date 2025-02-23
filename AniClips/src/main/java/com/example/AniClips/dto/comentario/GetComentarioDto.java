package com.example.AniClips.dto.comentario;

import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Comentario;
import com.example.AniClips.model.Valoracion;
import com.example.AniClips.security.user.dto.GetUsuarioClipDto;

import java.time.LocalDate;

public record GetComentarioDto(
    String texto,
    LocalDate fecha,
    GetUsuarioClipDto getUsuarioClipDto
) {

        public static GetComentarioDto of (Comentario comentario) {

            return new GetComentarioDto(
                    comentario.getTexto(),
                    comentario.getFecha(),
                    GetUsuarioClipDto.of(comentario.getUsuario())
            );
        }
    }
