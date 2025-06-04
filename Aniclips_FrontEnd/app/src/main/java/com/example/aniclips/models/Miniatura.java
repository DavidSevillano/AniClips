package com.example.aniclips.models;

public class Miniatura {

    private int duracion;
    private String urlMiniatura;
    private String nombreAnime;

    public Miniatura() {
    }
    public Miniatura(int duracion, String urlMiniatura, String nombreAnime) {
        this.duracion = duracion;
        this.urlMiniatura = urlMiniatura;
        this.nombreAnime = nombreAnime;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getUrlMiniatura() {
        return urlMiniatura;
    }

    public void setUrlMiniatura(String urlMiniatura) {
        this.urlMiniatura = urlMiniatura;
    }

    public String getNombreAnime() {
        return nombreAnime;
    }

    public void setNombreAnime(String nombreAnime) {
        this.nombreAnime = nombreAnime;
    }
}
