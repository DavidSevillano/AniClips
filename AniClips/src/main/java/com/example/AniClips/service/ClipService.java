package com.example.AniClips.service;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.files.dto.FileResponse;
import com.example.AniClips.files.model.FileMetadata;
import com.example.AniClips.files.service.StorageService;
import com.example.AniClips.model.Clip;
import com.example.AniClips.query.ClipSpecificationBuilder;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.util.SearchCriteria;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClipService {

    private final ClipRepository clipRepository;
    private final StorageService storageService;

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

    public Clip save(Usuario usuario, EditClipDto editClipDto) {

        FileResponse videoResponse = uploadFile(editClipDto.video());
        FileResponse miniaturaResponse = uploadFile(editClipDto.miniatura());

        Clip clip = Clip.builder()
                .urlVideo(videoResponse.uri())
                .nombreAnime(editClipDto.nombreAnime())
                .genero(editClipDto.genero())
                .miniatura(miniaturaResponse.uri())
                .descripcion(editClipDto.descripcion())
                .fecha(LocalDate.now())
                .usuario(usuario)
                .build();

        return clipRepository.save(clip);
    }

    private FileResponse uploadFile(MultipartFile multipartFile) {
        FileMetadata fileMetadata = storageService.store(multipartFile);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/clip/download/")
                .path(fileMetadata.getId())
                .toUriString();

        return FileResponse.builder()
                .id(fileMetadata.getId())
                .name(fileMetadata.getFilename())
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .uri(uri)
                .build();
    }

}
