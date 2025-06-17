package com.example.aniclips.dto;

import java.time.LocalDate;

public class ComentarioDto {
    private Long id;
    private String texto;
    private LocalDate fecha;
    private UsuarioClipDto getUsuarioClipDto;

    public ComentarioDto() {
        // Constructor por defecto
    }
    public ComentarioDto(Long id, String texto, LocalDate fecha, UsuarioClipDto getUsuarioClipDto) {
        this.id = id;
        this.texto = texto;
        this.fecha = fecha;
        this.getUsuarioClipDto = getUsuarioClipDto;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
