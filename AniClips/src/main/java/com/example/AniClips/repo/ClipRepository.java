package com.example.AniClips.repo;

import com.example.AniClips.model.Clip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClipRepository extends JpaRepository<Clip,Long> {
    @Query("""
           SELECT c 
           FROM Clip c
           LEFT JOIN FETCH c.valoraciones
           LEFT JOIN FETCH c.meGustas
           LEFT JOIN FETCH c.comentarios
           LEFT JOIN FETCH c.usuario
           """)
    List<Clip> findAllDetalles();

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
