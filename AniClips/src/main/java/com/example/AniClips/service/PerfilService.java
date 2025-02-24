package com.example.AniClips.service;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.dto.perfil.EditPerfilAvatarDto;
import com.example.AniClips.dto.perfil.EditPerfilDescripcionDto;
import com.example.AniClips.files.dto.FileResponse;
import com.example.AniClips.files.model.FileMetadata;
import com.example.AniClips.files.service.StorageService;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Perfil;
import com.example.AniClips.repo.ClipRepository;
import com.example.AniClips.repo.PerfilRepository;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.repo.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final StorageService storageService;
    private final UsuarioRepository usuarioRepository;
    private final ClipRepository clipRepository;

    @Transactional(readOnly = true)
    public Perfil findById(Usuario usuario) {

        Usuario usuario1 = usuarioRepository.findByIdAntiLazy(usuario.getId())
                .orElseThrow(() -> new EntityNotFoundException());

        return usuario1.getPerfil();
    }

    @Transactional
    public Perfil saveDescripcion(Usuario usuario, EditPerfilDescripcionDto dto) {

        Perfil perfil = Perfil.builder()
                .descripcion(dto.descripcion())
                .usuario(usuario)
                .build();

        return perfilRepository.save(perfil);
    }

    @Transactional
    public Perfil editDescripcion(Usuario usuario, EditPerfilDescripcionDto perfilDescripcionDto) {

        return perfilRepository.findById(usuario.getPerfil().getId())
                .map(old -> {
                        old.setUsuario(usuario);
                        old.setDescripcion(perfilDescripcionDto.descripcion());

                    return perfilRepository.save(old);
                })
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Perfil saveAvatar(Usuario usuario, EditPerfilAvatarDto editPerfilAvatarDto) {

        FileResponse foto = uploadFile(editPerfilAvatarDto.foto());

        Perfil perfil = Perfil.builder()
                .avatar(foto.uri())
                .usuario(usuario)
                .build();

        return perfilRepository.save(perfil);
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
