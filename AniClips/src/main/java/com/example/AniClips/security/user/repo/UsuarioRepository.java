package com.example.AniClips.security.user.repo;

import com.example.AniClips.security.user.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findFirstByUsername(String username);

    Optional<Usuario> findByActivationToken(String activationToken);
}
