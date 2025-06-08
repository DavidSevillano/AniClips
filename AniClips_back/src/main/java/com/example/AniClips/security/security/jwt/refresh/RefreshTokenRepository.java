package com.example.AniClips.security.security.jwt.refresh;

import java.util.UUID;

import com.example.AniClips.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {


    @Modifying
    @Transactional
    void deleteByUser(Usuario user);

}
