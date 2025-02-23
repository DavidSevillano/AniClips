package com.example.AniClips.security.user.controller;

import com.example.AniClips.security.security.jwt.access.JwtService;
import com.example.AniClips.security.security.jwt.refresh.RefreshToken;
import com.example.AniClips.security.security.jwt.refresh.RefreshTokenRequest;
import com.example.AniClips.security.security.jwt.refresh.RefreshTokenService;
import com.example.AniClips.security.user.dto.signupLogin.ActivateAccountRequest;
import com.example.AniClips.security.user.dto.signupLogin.CreateUserRequest;
import com.example.AniClips.security.user.dto.signupLogin.LoginRequest;
import com.example.AniClips.security.user.dto.signupLogin.UserResponse;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.service.UsuarioService;
import com.example.AniClips.security.util.SendGridMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final SendGridMailSender sendGridMailSender;


    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest createUserRequest) {
        Usuario user = usuarioService.createUser(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {


        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.username(),
                                loginRequest.password()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario user = (Usuario) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);

        // Generar el token de refresco
        RefreshToken refreshToken = refreshTokenService.create(user);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(user, accessToken, refreshToken.getToken()));

    }

    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(refreshTokenService.refreshToken(token));

    }

    @PostMapping("/activate/account/")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountRequest req) {
        String token = req.token();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(usuarioService.activateAccount(token)));
    }



    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal Usuario user) {
        return UserResponse.of(user);
    }

    @GetMapping("/me/admin")
    public Usuario adminMe(@AuthenticationPrincipal Usuario user) {
        return user;
    }

}