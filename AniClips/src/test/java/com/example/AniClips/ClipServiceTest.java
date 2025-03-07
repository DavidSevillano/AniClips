package com.example.AniClips;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.error.UnauthorizedAccessException;
import com.example.AniClips.files.dto.FileResponse;
import com.example.AniClips.files.model.FileMetadata;
import com.example.AniClips.files.service.StorageService;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.service.ClipService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
        usuario.setId(UUID.randomUUID());
        clip = new Clip();
        clip.setId(1L);
        clip.setUsuario(usuario);
        clip.setFecha(LocalDate.now());

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testFindAll() {

        Page<Clip> clipPage = new PageImpl<>(Collections.singletonList(clip));
        when(clipRepository.findAllDetalles(any(Pageable.class))).thenReturn(clipPage);

        Page<Clip> result = clipService.findAll(Pageable.ofSize(10));

        assertFalse(result.isEmpty());
        assertEquals(1, result.getTotalElements());
        verify(clipRepository, times(1)).findAllDetalles(any(Pageable.class));
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
    public void testSave() {
        EditClipDto editClipDto = new EditClipDto("Naruto", "Shonen", "Descripción", mock(MultipartFile.class), mock(MultipartFile.class));

        // Simular FileMetadata usando Mockito
        FileMetadata fileMetadata = mock(FileMetadata.class);
        when(fileMetadata.getId()).thenReturn("fileId");
        when(fileMetadata.getFilename()).thenReturn("video.mp4");

        when(storageService.store(any(MultipartFile.class))).thenReturn(fileMetadata);
        when(clipRepository.save(any(Clip.class))).thenReturn(clip);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/clip/save")
                .file("video", new byte[0])
                .file("miniatura", new byte[0]);

        Clip savedClip = clipService.save(usuario, editClipDto);

        assertNotNull(savedClip);
        assertEquals(clip.getId(), savedClip.getId());
        verify(clipRepository, times(1)).save(any(Clip.class));
    }

    @Test
    public void testEliminarMiClip() {
        clip.setUsuario(usuario);
        when(clipRepository.findById(1L)).thenReturn(Optional.of(clip));

        clipService.eliminarMiClip(usuario, 1L);

        verify(clipRepository, times(1)).deleteById(1L);
    }
}