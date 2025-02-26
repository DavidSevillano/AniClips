package com.example.AniClips.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Clip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreAnime;

    private String genero;

    private String descripcion;

    private String urlVideo;

    private LocalDate fecha;

    private int visitas;

    private int duracion;

    private String miniatura;


    @OneToMany(mappedBy = "clip", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private Set<MeGusta> meGustas = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id",
            foreignKey = @ForeignKey(name = "fk_usuario_clip"))
    @JsonIgnore
    private Usuario usuario;

    @OneToMany(mappedBy = "clip", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private Set<Valoracion> valoraciones = new HashSet<>();

    @OneToMany(mappedBy = "clip", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    @ToString.Exclude
    private Set<Comentario> comentarios = new HashSet<>();

    // Métodos helpers de MeGusta

    public void addMeGusta(MeGusta m) {
        m.setClip(this);
        this.getMeGustas().add(m);
    }

    public void removeMeGusta(MeGusta m) {
        this.getMeGustas().remove(m);
        m.setClip(null);
    }

    // Métodos helpers de Valoracion

    public void addValoracion(Valoracion v) {
        v.setClip(this);
        this.getValoraciones().add(v);
    }

    public void removeValoracion(Valoracion v) {
        this.getValoraciones().remove(v);
        v.setClip(null);
    }

    // Métodos helpers de Comentario

    public void addComentario(Comentario c) {
        c.setClip(this);
        this.getComentarios().add(c);
    }

    public void removeComentario(Comentario c) {
        this.getComentarios().remove(c);
        c.setClip(null);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Clip clip = (Clip) o;
        return getId() != null && Objects.equals(getId(), clip.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

