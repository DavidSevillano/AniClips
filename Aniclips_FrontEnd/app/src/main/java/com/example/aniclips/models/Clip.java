package com.example.aniclips.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Clip implements Serializable {

    private Long id;
    private String nombreAnime;
    private String genero;
    private String descripcion;
    private String urlVideo;
    private LocalDate fecha;
    private int visitas;
    private int duracion;
    private String miniatura;
    private Boolean ledioLike;
    private Boolean loRateo;

    private List<MeGusta> meGustas;
    private Usuario usuario;
    private List<Valoracion> valoraciones;
    private List<Comentario> comentarios;

    public Clip() {
    }

    public Clip(List<Comentario> comentarios, String descripcion, int duracion, LocalDate fecha, String genero, Long id, List<MeGusta> meGustas, String miniatura, String nombreAnime, String urlVideo, Usuario usuario, List<Valoracion> valoraciones, int visitas, Boolean ledioLike, Boolean loRateo) {
        this.comentarios = comentarios;
        this.descripcion = descripcion;
        this.duracion = duracion;
        this.fecha = fecha;
        this.genero = genero;
        this.id = id;
        this.meGustas = meGustas;
        this.miniatura = miniatura;
        this.nombreAnime = nombreAnime;
        this.urlVideo = urlVideo;
        this.usuario = usuario;
        this.valoraciones = valoraciones;
        this.visitas = visitas;
        this.ledioLike = ledioLike;
        this.loRateo = loRateo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAnime() {
        return nombreAnime;
    }

    public void setNombreAnime(String nombreAnime) {
        this.nombreAnime = nombreAnime;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
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

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }

    public List<MeGusta> getMeGustas() {
        return meGustas;
    }

    public void setMeGustas(List<MeGusta> meGustas) {
        this.meGustas = meGustas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
    public Boolean getLedioLike() {
        return ledioLike;
    }
    public void setLedioLike(Boolean ledioLike) {
        this.ledioLike = ledioLike;
    }
    public Boolean getLoRateo() {
        return loRateo;
    }
    public void setLoRateo(Boolean loRateo) {
        this.loRateo = loRateo;
    }
}


