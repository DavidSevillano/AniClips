package com.example.AniClips.security.user.service;

import java.util.Set;

import com.example.AniClips.security.user.dto.CreateUserRequest;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.model.UserRole;
import com.example.AniClips.security.user.repo.UserRepository;
import lombok.Generated;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario createUser(CreateUserRequest createUserRequest) {
        Usuario usuario = Usuario.builder().username(createUserRequest.username()).password(this.passwordEncoder.encode(createUserRequest.password())).roles(Set.of(UserRole.USER)).build();
        return (Usuario)this.userRepository.save(usuario);
    }

    @Generated
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
}
