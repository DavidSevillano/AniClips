package com.example.AniClips.security.user.controller;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.security.security.jwt.access.JwtService;
import com.example.AniClips.security.security.jwt.refresh.RefreshToken;
import com.example.AniClips.security.security.jwt.refresh.RefreshTokenRequest;
import com.example.AniClips.security.security.jwt.refresh.RefreshTokenService;
import com.example.AniClips.security.user.dto.EditSeguidoDto;
import com.example.AniClips.security.user.dto.GetUsuarioClipDto;
import com.example.AniClips.security.user.dto.signupLogin.ActivateAccountRequest;
import com.example.AniClips.security.user.dto.signupLogin.CreateUserRequest;
import com.example.AniClips.security.user.dto.signupLogin.LoginRequest;
import com.example.AniClips.security.user.dto.signupLogin.UserResponse;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.service.UsuarioService;
import com.example.AniClips.security.util.SendGridMailSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final SendGridMailSender sendGridMailSender;


    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest createUserRequest) {
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

    @Operation(summary = "Sigue a un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Usuario seguido",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetUsuarioClipDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "Username": "naruto",
                                                "getPerfilAvatarDto": {
                                                    "avatar": "https://example.com/naruto"
                                                }
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun usuario",
                    content = @Content),
    })
    @PostMapping("/seguir/")
    public ResponseEntity<GetUsuarioClipDto> seguirUsuario(@RequestBody EditSeguidoDto seguidoDto) {
        Usuario usuario = usuarioService.seguir(seguidoDto);

        GetUsuarioClipDto getUsuarioClipDto = GetUsuarioClipDto.of(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(getUsuarioClipDto);
    }

    @DeleteMapping("/dejar-de-seguir/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        usuarioService.dejarDeSeguir(id);
        return ResponseEntity.noContent().build();
    }
}



