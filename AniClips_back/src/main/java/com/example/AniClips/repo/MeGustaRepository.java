package com.example.AniClips.repo;

import com.example.AniClips.model.MeGusta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MeGustaRepository extends JpaRepository<MeGusta,Long> {
    Optional<MeGusta> findMeGustaByUsuarioIdAndClipId(UUID usuarioId, Long clipId);
}
