package com.example.AniClips.service;

import com.example.AniClips.dto.Valoracion.EditValoracionDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Valoracion;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.repo.ValoracionRepository;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.repo.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClipRepository clipRepository;

    @Transactional
    public Valoracion save(Usuario usuario, EditValoracionDto dto) {

        Clip clip = clipRepository.findById(dto.clipId())
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado ningun clip con id " + dto.clipId()));

        Valoracion valoracion = Valoracion.builder()
                .usuario(usuario)
                .clip(clip)
                .fecha(LocalDate.now())
                .puntuacion(dto.puntuacion())
                .build();

        return valoracionRepository.save(valoracion);
    }
}
