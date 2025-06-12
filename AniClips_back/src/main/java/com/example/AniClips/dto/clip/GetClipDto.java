package com.example.AniClips.dto.clip;

import com.example.AniClips.dto.user.GetUsuarioClipDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Usuario;
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
        double mediaValoraciones,
        boolean ledioLike,
        boolean loRateo,
        boolean loSigue
) {
    public static GetClipDto of(Clip clip, Usuario usuarioActual, boolean loSigue) {
        double mediaValoraciones = clip.getValoraciones().isEmpty()
                ? 0.0
                : clip.getValoraciones().stream()
                .mapToDouble(Valoracion::getPuntuacion)
                .average()
                .orElse(0.0);

        boolean leDioLike = usuarioActual != null && clip.getMeGustas().stream()
                .anyMatch(meGusta -> meGusta.getUsuario().getId().equals(usuarioActual.getId()));

        boolean loValoro = usuarioActual != null && clip.getValoraciones().stream()
                .anyMatch(valoracion -> valoracion.getUsuario().getId().equals(usuarioActual.getId()));

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
                mediaValoraciones,
                leDioLike,
                loValoro,
                loSigue
        );
    }
}