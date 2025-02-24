package com.example.AniClips.service;

import com.example.AniClips.dto.perfil.EditPerfilDescripcionDto;
import com.example.AniClips.model.Perfil;
import com.example.AniClips.repo.PerfilRepository;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.repo.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Perfil save(Usuario usuario, EditPerfilDescripcionDto dto) {

        Perfil perfil = Perfil.builder()
                .descripcion(dto.descripcion())
                .usuario(usuario)
                .build();

        return perfilRepository.save(perfil);
    }

    @Transactional
    public Perfil edit(Usuario usuario, EditPerfilDescripcionDto perfilDescripcionDto) {

        return perfilRepository.findById(usuario.getPerfil().getId())
                .map(old -> {
                        old.setUsuario(usuario);
                        old.setDescripcion(perfilDescripcionDto.descripcion());

                    return perfilRepository.save(old);
                })
                .orElseThrow(() -> new EntityNotFoundException());
    }
}
