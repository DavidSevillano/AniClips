package com.example.AniClips.security.user.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.security.user.dto.EditSeguidoDto;
import com.example.AniClips.security.user.dto.signupLogin.CreateUserRequest;
import com.example.AniClips.security.user.error.ActivationExpiredException;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.model.UserRole;
import com.example.AniClips.security.user.repo.UsuarioRepository;
import com.example.AniClips.security.util.SendGridMailSender;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final SendGridMailSender mailSender;
    //private final ResendMailSender mailSender;


    @Value("${activation.duration}")
    private int activationDuration;

    public Usuario createUser(CreateUserRequest createUserRequest) {
        Usuario user = Usuario.builder()
                .username(createUserRequest.username())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .email(createUserRequest.email())
                .roles(Set.of(UserRole.USER))
                .activationToken(generateRandomActivationCode())
                .build();

        try {
            mailSender.sendMail(createUserRequest.email(), "Activación de cuenta", user.getActivationToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al enviar el email de activación");
        }


        return usuarioRepository.save(user);
    }

    public String generateRandomActivationCode() {
        return UUID.randomUUID().toString();
    }

    public Usuario activateAccount(String token) {

        return usuarioRepository.findByActivationToken(token)
                .filter(user -> ChronoUnit.MINUTES.between(Instant.now(), user.getCreatedAt()) - activationDuration < 0)
                .map(user -> {
                    user.setEnabled(true);
                    user.setActivationToken(null);
                    return usuarioRepository.save(user);
                })
                .orElseThrow(() -> new ActivationExpiredException("El código de activación no existe o ha caducado"));
    }

    @Transactional
    public Usuario seguir(Usuario usuario, EditSeguidoDto editSeguidoDto) {

        Usuario seguido = usuarioRepository.findById(editSeguidoDto.seguidoId())
                .orElseThrow(() -> new EntityNotFoundException());


        usuario.addSeguido(seguido);

        usuarioRepository.flush();

        return seguido;
    }



}