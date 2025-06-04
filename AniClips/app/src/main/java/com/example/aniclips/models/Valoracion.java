package com.example.aniclips.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Valoracion implements Serializable {

    private Long id;

    private double puntuacion;

    private LocalDate fecha;

    private Usuario usuario;

    private Clip clip;

    public Valoracion(Clip clip, LocalDate fecha, Long id, double puntuacion, Usuario usuario) {
        this.clip = clip;
        this.fecha = fecha;
        this.id = id;
        this.puntuacion = puntuacion;
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

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
