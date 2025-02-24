package com.example.AniClips.repo;

import com.example.AniClips.model.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findFirstByUsername(String username);

    Optional<Usuario> findByActivationToken(String activationToken);

    boolean existsByUsername(String username);

    @Query("""
            SELECT u FROM Usuario u
            LEFT JOIN FETCH u.clips
            LEFT JOIN FETCH u.seguidores
            LEFT JOIN FETCH u.seguidos
            LEFT JOIN FETCH u.perfil
            WHERE u.id = :id
            """)
    Optional<Usuario> findByIdAntiLazy(@Param("id") UUID id);



}
