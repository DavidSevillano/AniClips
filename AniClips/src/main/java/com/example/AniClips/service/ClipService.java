package com.example.AniClips.service;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.query.ClipSpecificationBuilder;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.repo.UsuarioRepository;
import com.example.AniClips.util.SearchCriteria;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClipService {

    private final ClipRepository clipRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Clip> findAll() {
        List<Clip> result = clipRepository.findAllDetalles();
        if (result.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Clip findById(Long id) {
        Optional<Clip> clip = clipRepository.findByIdDetalles(id);

        if (clip.isPresent()) {
            return clip.get();
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public List<Clip> search(List<SearchCriteria> searchCriteriaList) {
        ClipSpecificationBuilder userSpecificationBuilder = new ClipSpecificationBuilder(searchCriteriaList);
        Specification<Clip> where = userSpecificationBuilder.build();

        List<Clip> result = clipRepository.findAll(where);

        if (result.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron clips con los criterios especificados.");
        }

        return result;
    }

    @Transactional
    public Clip save(EditClipDto editClipDto) {

        Usuario usuario = usuarioRepository.findById(editClipDto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException());

        Clip clip = Clip.builder()
                .urlVideo(editClipDto.urlClip())
                .nombreAnime(editClipDto.nombreAnime())
                .genero(editClipDto.genero())
                .miniatura(editClipDto.urlMiniatura())
                .descripcion(editClipDto.descripcion())
                .fecha(LocalDate.now())
                .usuario(usuario)
                .build();

        return clipRepository.save(clip);
    }

}
