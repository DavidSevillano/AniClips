package com.example.AniClips.repo;

import com.example.AniClips.model.MeGusta;
import com.example.AniClips.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ValoracionRepository extends JpaRepository<Valoracion,Long> {

}
