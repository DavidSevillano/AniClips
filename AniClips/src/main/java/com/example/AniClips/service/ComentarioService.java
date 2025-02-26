package com.example.AniClips.service;

import com.example.AniClips.dto.comentario.EditComentarioDto;
import com.example.AniClips.dto.perfil.EditPerfilDescripcionDto;
import com.example.AniClips.error.UnauthorizedAccessException;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Comentario;
import com.example.AniClips.model.Perfil;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.repo.ComentarioRepository;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.repo.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComentarioService {


    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClipRepository clipRepository;

    @Transactional(readOnly = true)
    public Page<Comentario> findAll(Pageable pageable) {
        Page<Comentario> result = comentarioRepository.findAll(pageable);
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

    @Transactional
    public void eliminarMiComentario(Usuario usuario, Long comentarioId) {

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("Comentario no encontrado"));

        if (!comentario.getUsuario().getId().equals(usuario.getId())) {
            throw new UnauthorizedAccessException("No tienes permiso para eliminar este comentario.");
        }

        comentarioRepository.deleteById(comentarioId);
    }

    @Transactional
    public void eliminarComentario(Long comentarioId) {

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new EntityNotFoundException("No se ha encontrado ningun comentario con id " + comentarioId));

        comentarioRepository.deleteById(comentarioId);

    }

}
