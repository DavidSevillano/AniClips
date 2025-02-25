package com.example.AniClips.service;

import com.example.AniClips.dto.comentario.EditComentarioDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Comentario;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.repo.ComentarioRepository;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.repo.UsuarioRepository;
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
    private final UsuarioRepository usuarioRepository;
    private final ClipRepository clipRepository;

    @Transactional(readOnly = true)
    public List<Comentario> findAll() {
        List<Comentario> result = comentarioRepository.findAll();
        if (result.isEmpty()) {
            throw new EntityNotFoundException("No se han encontrado comentarios");
        }
        return result;
    }

    public Comentario save(Usuario usuario, EditComentarioDto dto) {

        Clip clip = clipRepository.findById(dto.clipId())
                .orElseThrow(() -> new EntityNotFoundException("No existe ningun clip con id " + dto.clipId()));

        Comentario comentario = Comentario.builder()
                .usuario(usuario)
                .clip(clip)
                .fecha(LocalDate.now())
                .texto(dto.texto())
                .build();

        return comentarioRepository.save(comentario);
    }

}
