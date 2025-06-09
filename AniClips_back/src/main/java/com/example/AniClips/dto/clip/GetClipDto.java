package com.example.AniClips.dto.clip;

import com.example.AniClips.dto.user.GetUsuarioClipDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Valoracion;

import java.time.LocalDate;

public record GetClipDto(
        Long id,
        String descripcion,
        String urlVideo,
        String urlMiniatura,
        LocalDate fecha,
        int visitas,
        int duracion,
        GetUsuarioClipDto getUsuarioClipDto,
        int cantidadMeGusta,
        int cantidadComentarios,
        double mediaValoraciones

) {


    public static GetClipDto of(Clip clip) {

        double mediaValoraciones = clip.getValoraciones().isEmpty()
                ? 0.0
                : clip.getValoraciones().stream()
                .mapToDouble(Valoracion::getPuntuacion)
                .average()
                .orElse(0.0);

        return new GetClipDto(
                clip.getId(),
                clip.getDescripcion(),
                clip.getUrlVideo(),
                clip.getMiniatura(),
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
