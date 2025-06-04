package com.example.aniclips.models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Usuario {

    private UUID id;
    private String username;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();
    private boolean enabled;
    private String activationToken;
    private Instant createdAt;

    private List<MeGusta> meGusta = new ArrayList<>();
    private List<Clip> clips = new ArrayList<>();
    private List<Valoracion> valoracion = new ArrayList<>();
    private List<Comentario> comentario = new ArrayList<>();
    private Perfil perfil;

    private Set<Usuario> seguidos = new HashSet<>();
    private Set<Usuario> seguidores = new HashSet<>();

    public Usuario() {
    }

    public Usuario(String activationToken, List<Clip> clips, List<Comentario> comentario, Instant createdAt, String email, boolean enabled, UUID id, List<MeGusta> meGusta, String password, Perfil perfil, Set<String> roles, Set<Usuario> seguidores, Set<Usuario> seguidos, String username, List<Valoracion> valoracion) {
        this.activationToken = activationToken;
        this.clips = clips;
        this.comentario = comentario;
        this.createdAt = createdAt;
        this.email = email;
        this.enabled = enabled;
        this.id = id;
        this.meGusta = meGusta;
        this.password = password;
        this.perfil = perfil;
        this.roles = roles;
        this.seguidores = seguidores;
        this.seguidos = seguidos;
        this.username = username;
        this.valoracion = valoracion;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<MeGusta> getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(List<MeGusta> meGusta) {
        this.meGusta = meGusta;
    }

    public List<Clip> getClips() {
        return clips;
    }

    public void setClips(List<Clip> clips) {
        this.clips = clips;
    }

    public List<Valoracion> getValoracion() {
        return valoracion;
    }

    public void setValoracion(List<Valoracion> valoracion) {
        this.valoracion = valoracion;
    }

    public List<Comentario> getComentario() {
        return comentario;
    }

    public void setComentario(List<Comentario> comentario) {
        this.comentario = comentario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Set<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(Set<Usuario> seguidos) {
        this.seguidos = seguidos;
    }

    public Set<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(Set<Usuario> seguidores) {
        this.seguidores = seguidores;
    }
}
