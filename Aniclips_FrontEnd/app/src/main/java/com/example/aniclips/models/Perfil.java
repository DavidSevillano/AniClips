package com.example.aniclips.models;

import java.io.Serializable;

public class Perfil implements Serializable {

    private Long id;

    private String avatar;

    private String descripcion;

    private Usuario usuario;

    public Perfil(String avatar, String descripcion, Long id, Usuario usuario) {
        this.avatar = avatar;
        this.descripcion = descripcion;
        this.id = id;
        this.usuario = usuario;
    }

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
