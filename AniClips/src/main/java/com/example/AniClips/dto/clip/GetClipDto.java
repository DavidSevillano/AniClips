package com.example.AniClips.dto.clip;

import com.example.AniClips.dto.user.GetUsuarioClipDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Valoracion;

import java.time.LocalDate;

public record GetClipDto (
    String descripcion,
    String urlVideo,
    LocalDate fecha,
    int visitas,
    int duracion,
    GetUsuarioClipDto getUsuarioClipDto,
    int cantidadMeGusta,
    int cantidadComentarios,
    double mediaValoraciones

) {


        public static GetClipDto of (Clip clip) {

            double mediaValoraciones = clip.getValoraciones().isEmpty()
                    ? 0.0
                    : clip.getValoraciones().stream()
                    .mapToDouble(Valoracion::getPuntuacion)
                    .average()
                    .orElse(0.0);

            return new GetClipDto(
                    clip.getDescripcion(),
                    clip.getUrlVideo(),
                    clip.getFecha(),
                    clip.getVisitas(),
                    clip.getDuracion(),
                    GetUsuarioClipDto.of(clip.getUsuario()),
                    clip.getMeGustas().size(),
                    clip.getComentarios().size(),
                    mediaValoraciones
            );
        }
    }
