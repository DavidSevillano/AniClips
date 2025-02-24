package com.example.AniClips.service;

import com.example.AniClips.model.Clip;
import com.example.AniClips.model.MeGusta;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.repo.MeGustaRepository;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.repo.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MeGustaService {

    private final MeGustaRepository meGustaRepository;
    private final ClipRepository clipRepository;

    @Transactional
    public MeGusta save(Usuario usuario, Long clipId) {

        Clip clip = clipRepository.findById(clipId)
                .orElseThrow(() -> new EntityNotFoundException());

        MeGusta meGusta = MeGusta.builder()
                .usuario(usuario)
                .clip(clip)
                .fecha(LocalDate.now())
                .build();

        return meGustaRepository.save(meGusta);
    }
}
