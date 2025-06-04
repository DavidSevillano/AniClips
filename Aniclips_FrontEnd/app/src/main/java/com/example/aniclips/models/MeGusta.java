package com.example.aniclips.models;

import java.io.Serializable;
import java.time.LocalDate;

public class MeGusta implements Serializable {

    private Long id;

    private LocalDate fecha;

    private Usuario usuario;

    private Clip clip;

    public MeGusta(Clip clip, LocalDate fecha, Long id, Usuario usuario) {
        this.clip = clip;
        this.fecha = fecha;
        this.id = id;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
