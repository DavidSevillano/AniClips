package com.example.AniClips.model;

import com.example.AniClips.security.user.model.Usuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private String descripcion;

    private String url;

    private LocalDate fecha;

    private int visitas;

    private int duracion;

    private byte miniatura;


    @OneToMany(mappedBy = "clip", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<MeGusta> meGusta = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "usuario_id",
            foreignKey = @ForeignKey(name = "fk_usuario_clip"))
    private Usuario usuario;

    @OneToMany(mappedBy = "clip", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Valoracion> valoracion = new ArrayList<>();

    @OneToMany(mappedBy = "clip", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Comentario> comentario = new ArrayList<>();

    // Métodos helpers de MeGusta

    public void addMeGusta(MeGusta m) {
        m.setClip(this);
        this.getMeGusta().add(m);
    }

    public void removeMeGusta(MeGusta m) {
        this.getMeGusta().remove(m);
        m.setClip(null);
    }

    // Métodos helpers de Valoracion

    public void addValoracion(Valoracion v) {
        v.setClip(this);
        this.getValoracion().add(v);
    }

    public void removeValoracion(Valoracion v) {
        this.getValoracion().remove(v);
        v.setClip(null);
    }

    // Métodos helpers de Comentario

    public void addComentario(Comentario c) {
        c.setClip(this);
        this.getComentario().add(c);
    }

    public void removeComentario(Valoracion c) {
        this.getComentario().remove(c);
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

