package com.example.AniClips.security.user.controller;

import com.example.AniClips.security.security.jwt.access.JwtService;
import com.example.AniClips.security.security.jwt.refresh.RefreshToken;
import com.example.AniClips.security.security.jwt.refresh.RefreshTokenService;
import com.example.AniClips.security.user.dto.CreateUserRequest;
import com.example.AniClips.security.user.dto.LoginRequest;
import com.example.AniClips.security.user.dto.UserResponse;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.service.UserService;
import lombok.Generated;
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
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping({"/auth/register"})
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest createUserRequest) {
        Usuario usuario = this.userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(usuario));
    }

    @PostMapping({"/auth/login"})
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Usuario usuario = (Usuario)authentication.getPrincipal();
        String accessToken = this.jwtService.generateAccessToken(usuario);
        RefreshToken refreshToken = this.refreshTokenService.create(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(usuario, accessToken, refreshToken.getToken()));
    }

    @GetMapping({"/me"})
    public UserResponse me(@AuthenticationPrincipal Usuario usuario) {
        return UserResponse.of(usuario);
    }

    @GetMapping({"/me/admin"})
    public Usuario adminMe(@AuthenticationPrincipal Usuario usuario) {
        return usuario;
    }

    @Generated
    public UserController(final UserService userService, final AuthenticationManager authenticationManager, final JwtService jwtService, final RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }
}