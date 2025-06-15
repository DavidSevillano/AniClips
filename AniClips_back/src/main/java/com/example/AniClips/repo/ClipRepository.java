package com.example.AniClips.repo;

import com.example.AniClips.model.Clip;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ClipRepository extends JpaRepository<Clip,Long>, JpaSpecificationExecutor<Clip> {
    @Query("""
           SELECT c 
           FROM Clip c
           LEFT JOIN FETCH c.valoraciones
           LEFT JOIN FETCH c.meGustas
           LEFT JOIN FETCH c.comentarios
           LEFT JOIN FETCH c.usuario
           """)
       Page<Clip> findAllDetalles(org.springframework.data.domain.Pageable pageRequest);

    @EntityGraph(attributePaths = {"usuario", "valoraciones", "meGustas", "comentarios"})
    @Query("""
            SELECT c
            FROM Clip c
            JOIN c.usuario u
            WHERE u IN (
                SELECT s FROM Usuario us JOIN us.seguidos s WHERE us.id = :usuarioId
            )
            """)
    Page<Clip> findAllByUsuariosSeguidos(UUID usuarioId, Pageable pageRequest);

    @Query("""
           SELECT c FROM Clip c
           LEFT JOIN FETCH c.valoraciones
           LEFT JOIN FETCH c.meGustas
           LEFT JOIN FETCH c.comentarios
           LEFT JOIN FETCH c.usuario
           WHERE c.id = ?1
           """
    )
    Optional<Clip> findByIdDetalles(Long id);
}
