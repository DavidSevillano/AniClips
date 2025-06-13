package com.example.aniclips.models;

public class Miniatura {

    private Long id;
    private String urlMiniatura;
    private String nombreAnime;

    public Miniatura() {
    }
    public Miniatura(Long id, String urlMiniatura, String nombreAnime) {
        this.id = id;
        this.urlMiniatura = urlMiniatura;
        this.nombreAnime = nombreAnime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
