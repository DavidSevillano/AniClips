package com.example.AniClips.service;

import com.example.AniClips.dto.Valoracion.EditValoracionDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Comentario;
import com.example.AniClips.model.Valoracion;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.repo.ComentarioRepository;
import com.example.AniClips.repo.ValoracionRepository;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.repo.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @Transactional(readOnly = true)
    public List<Comentario> findAll() {
        List<Comentario> result = comentarioRepository.findAll();
        if (result.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return result;
    }

}
