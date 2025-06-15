package com.example.aniclips.dto;

import java.time.LocalDate;

public class ClipDto {
    private Long id;
    private String descripcion;
    private String urlVideo;
    private String urlMiniatura;
    private String fecha;
    private int visitas;
    private int duracion;
    private UsuarioClipDto getUsuarioClipDto;
    private int cantidadMeGusta;
    private int cantidadComentarios;
    private int cantidadValoraciones;
    private double mediaValoraciones;
    private boolean ledioLike;
    private boolean loRateo;
    private boolean loSigue;

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public UsuarioClipDto getGetUsuarioClipDto() {
        return getUsuarioClipDto;
    }

    public void setGetUsuarioClipDto(UsuarioClipDto getUsuarioClipDto) {
        this.getUsuarioClipDto = getUsuarioClipDto;
    }

    public int getCantidadMeGusta() {
        return cantidadMeGusta;
    }

    public void setCantidadMeGusta(int cantidadMeGusta) {
        this.cantidadMeGusta = cantidadMeGusta;
    }

    public int getCantidadComentarios() {
        return cantidadComentarios;
    }

    public void setCantidadComentarios(int cantidadComentarios) {
        this.cantidadComentarios = cantidadComentarios;
    }
    public int getCantidadValoraciones() {
        return cantidadValoraciones;
    }
    public void setCantidadValoraciones(int cantidadValoraciones) {
        this.cantidadValoraciones = cantidadValoraciones;
    }

    public double getMediaValoraciones() {
        return mediaValoraciones;
    }

    public void setMediaValoraciones(double mediaValoraciones) {
        this.mediaValoraciones = mediaValoraciones;
    }
    public String getUrlMiniatura() {
        return urlMiniatura;
    }
    public void setUrlMiniatura(String urlMiniatura) {
        this.urlMiniatura = urlMiniatura;
    }
    public boolean isLedioLike() {
        return ledioLike;
    }
    public void setLedioLike(boolean ledioLike) {
        this.ledioLike = ledioLike;
    }
    public boolean isLoRateo() {
        return loRateo;
    }
    public void setLoRateo(boolean loRateo) {
        this.loRateo = loRateo;
    }
    public boolean isLoSigue() {
        return loSigue;
    }
    public void setLoSigue(boolean loSigue) {
        this.loSigue = loSigue;
    }
}
