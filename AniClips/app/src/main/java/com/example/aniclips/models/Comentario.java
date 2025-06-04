package com.example.aniclips.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Comentario implements Serializable {

    private Long id;
    private LocalDate fecha;

    private String texto;

    private Usuario usuario;

    private Clip clip;

    public Comentario(Clip clip, LocalDate fecha, Long id, String texto, Usuario usuario) {
        this.clip = clip;
        this.fecha = fecha;
        this.id = id;
        this.texto = texto;
        this.usuario = usuario;
    }

    public Clip getClip() {
        return clip;
    }

    public void setClip(Clip clip) {
        this.clip = clip;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
