package com.example.AniClips.security.user.model;

import com.example.AniClips.model.*;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.Generated;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(
        name = "user_entity"
)
public class Usuario {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @NaturalId
    @Column(
            unique = true,
            updatable = false
    )
    private String username;

    private String password;

    @ElementCollection(
            fetch = FetchType.EAGER
    )
    private Set<UserRole> roles;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    private MeGusta meGusta;

    private Clip clip;

    private Valoracion valoracion;

    private Comentario comentario;

    private Seguidor seguidor;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection)this.roles.stream().map((role) -> "ROLE_" + String.valueOf(role)).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Generated
    public static UserBuilder builder() {
        return new UserBuilder();
    }


}
