package com.example.AniClips.repo;

import com.example.AniClips.model.Comentario;
import com.example.AniClips.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

}
