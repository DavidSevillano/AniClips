package com.example.aniclips.dto;

public class ClipDtoMiniatura {
    private long id;
    private String miniatura;
    private String nombreAnime;

    // Getters y setters
    public long getId() { return id; }
    public String getMiniatura() { return miniatura; }
    public String getNombreAnime() { return nombreAnime; }
    public void setId(long id) { this.id = id; }
    public void setMiniatura(String miniatura) { this.miniatura = miniatura; }
    public void setNombreAnime(String nombreAnime) { this.nombreAnime = nombreAnime; }
}