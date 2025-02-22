package com.example.AniClips.service;

import com.example.AniClips.model.Clip;
import com.example.AniClips.repo.ClipRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClipService {

    private final ClipRepository clipRepository;

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

}
