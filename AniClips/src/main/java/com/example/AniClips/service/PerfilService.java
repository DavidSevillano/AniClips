package com.example.AniClips.service;

import com.example.AniClips.dto.meGusta.EditMeGustaDto;
import com.example.AniClips.dto.perfil.EditPerfilDescripcionDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.MeGusta;
import com.example.AniClips.model.Perfil;
import com.example.AniClips.repo.PerfilRepository;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.repo.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Perfil save(EditPerfilDescripcionDto dto) {

        Usuario usuario = usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException());

        Perfil perfil = Perfil.builder()
                .descripcion(dto.descripcion())
                .usuario(usuario)
                .build();

        return perfilRepository.save(perfil);
    }

    @Transactional
    public Perfil edit(EditPerfilDescripcionDto perfilDescripcionDto) {

        Usuario usuario = usuarioRepository.findById(perfilDescripcionDto.usuarioId())
                .orElseThrow(() -> new EntityNotFoundException());

        return perfilRepository.findById(usuario.getPerfil().getId())
                .map(old -> {
                        old.setUsuario(usuario);
                        old.setDescripcion(perfilDescripcionDto.descripcion());

                    return perfilRepository.save(old);
                })
                .orElseThrow(() -> new EntityNotFoundException());
    }
}
