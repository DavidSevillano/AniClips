package com.example.aniclips.dto;

import java.time.LocalDate;

public class ComentarioDto {
    private String texto;
    private LocalDate fecha;
    private UsuarioClipDto getUsuarioClipDto;

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public UsuarioClipDto getGetUsuarioClipDto() {
        return getUsuarioClipDto;
    }
    public void setGetUsuarioClipDto(UsuarioClipDto getUsuarioClipDto) {
        this.getUsuarioClipDto = getUsuarioClipDto;
    }
}
