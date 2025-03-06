package com.example.AniClips;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.error.UnauthorizedAccessException;
import com.example.AniClips.files.model.FileMetadata;
import com.example.AniClips.files.service.local.LocalFileMetadataImpl;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.service.ClipService;
import com.example.AniClips.files.service.StorageService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class ClipServiceTest {

    @InjectMocks
    private ClipService clipService;

    @Mock
    private ClipRepository clipRepository;

    @Mock
    private StorageService storageService;

    private Clip clip;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
        clip = new Clip();
        clip.setId(1L);
        clip.setUsuario(usuario);
        clip.setFecha(LocalDate.now());
    }

    @Test
    public void testSaveClip() {
        MockMultipartFile videoFile = new MockMultipartFile("video", "video.mp4", "video/mp4", new byte[0]);
        MockMultipartFile miniaturaFile = new MockMultipartFile("miniatura", "miniatura.jpg", "image/jpeg", new byte[0]);

        EditClipDto editClipDto = new EditClipDto("Naruto", "Shonen", "Descripción", videoFile, miniaturaFile);

        FileMetadata videoMetadata = LocalFileMetadataImpl.of("video.mp4");
        FileMetadata miniaturaMetadata = LocalFileMetadataImpl.of("miniatura.jpg");

        when(storageService.store(any(MultipartFile.class))).thenReturn(videoMetadata);
        when(storageService.store(any(MultipartFile.class))).thenReturn(miniaturaMetadata);

        when(clipRepository.save(any(Clip.class))).thenReturn(clip);

        Clip savedClip = clipService.save(usuario, editClipDto);

        assertNotNull(savedClip);
        assertEquals(clip.getId(), savedClip.getId());
        verify(clipRepository, times(1)).save(any(Clip.class));
    }



    @Test
    public void testFindAllClips() {
        Pageable pageable = Pageable.ofSize(10);
        Page<Clip> clipPage = new PageImpl<>(Collections.singletonList(clip));

        when(clipRepository.findAllDetalles(pageable)).thenReturn(clipPage);

        Page<Clip> result = clipService.findAll(pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        verify(clipRepository, times(1)).findAllDetalles(pageable);
    }

    @Test
    public void testFindById() {
        when(clipRepository.findByIdDetalles(1L)).thenReturn(Optional.of(clip));

        Clip foundClip = clipService.findById(1L);

        assertNotNull(foundClip);
        assertEquals(clip.getId(), foundClip.getId());
        verify(clipRepository, times(1)).findByIdDetalles(1L);
    }

    @Test
    public void testFindById_NotFound() {
        when(clipRepository.findByIdDetalles(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            clipService.findById(1L);
        });

        assertEquals("Clip no encontrado", exception.getMessage());
        verify(clipRepository, times(1)).findByIdDetalles(1L);
    }

    @Test
    public void testEliminarMiClip() {
        clip.setUsuario(usuario);
        when(clipRepository.findById(1L)).thenReturn(Optional.of(clip));

        clipService.eliminarMiClip(usuario, 1L);

        verify(clipRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testEliminarMiClip_Unauthorized() {
        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(UUID.randomUUID());
        clip.setUsuario(otroUsuario);

        when(clipRepository.findById(1L)).thenReturn(Optional.of(clip));

        Exception exception = assertThrows(UnauthorizedAccessException.class, () -> {
            clipService.eliminarMiClip(usuario, 1L);
        });

        assertEquals("No tienes permiso para eliminar este clip.", exception.getMessage());
        verify(clipRepository, never()).deleteById(1L);
    }
}