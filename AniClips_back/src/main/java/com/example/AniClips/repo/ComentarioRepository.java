package com.example.AniClips.repo;

import com.example.AniClips.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

    Page<Comentario> findByClipId(Long clipId, Pageable pageable);

}
