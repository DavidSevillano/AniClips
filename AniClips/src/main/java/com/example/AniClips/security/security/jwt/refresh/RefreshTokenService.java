package com.example.AniClips.security.security.jwt.refresh;

import java.time.Instant;
import java.util.UUID;

import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.repo.UserRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(
        readOnly = true
)
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    @Value("${jwt.refresh.duration}")
    private int durationInMinutes;

    @Transactional
    public RefreshToken create(Usuario usuario) {
        return (RefreshToken)this.refreshTokenRepository.save(RefreshToken.builder().user(usuario).token(UUID.randomUUID().toString()).expireAt(Instant.now().plusSeconds((long)(this.durationInMinutes * 60))).build());
    }

    @Generated
    public RefreshTokenService(final RefreshTokenRepository refreshTokenRepository, final UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }
}
