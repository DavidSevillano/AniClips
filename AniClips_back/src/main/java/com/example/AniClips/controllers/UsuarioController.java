package com.example.AniClips.controllers;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.security.security.jwt.access.JwtService;
import com.example.AniClips.security.security.jwt.refresh.RefreshToken;
import com.example.AniClips.security.security.jwt.refresh.RefreshTokenRequest;
import com.example.AniClips.security.security.jwt.refresh.RefreshTokenService;
import com.example.AniClips.dto.user.EditSeguidoDto;
import com.example.AniClips.dto.user.GetUsuarioClipDto;
import com.example.AniClips.dto.user.signupLogin.ActivateAccountRequest;
import com.example.AniClips.dto.user.signupLogin.CreateUserRequest;
import com.example.AniClips.dto.user.signupLogin.LoginRequest;
import com.example.AniClips.dto.user.signupLogin.UserResponse;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.service.UsuarioService;
import com.example.AniClips.security.util.SendGridMailSender;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "usuario", description = "Controlador usuario")
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "Registra un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Usuario registrado",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "64ac35b8-a0c3-4352-9da9-1a8ed58b20f2",
                                                "username": "Buri"
                                            }
                                            """
                            )}
                    )}),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody CreateUserRequest createUserRequest) {
        Usuario user = usuarioService.createUser(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(UserResponse.of(user));
    }

    @Operation(summary = "Logea un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Usuario logeado",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LoginRequest.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "64ac35b8-a0c3-4352-9da9-1a8ed58b20f2",
                                                "username": "Buri",
                                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2NGFjMzViOC1hMGMzLTQzNTItOWRhOS0xYThlZDU4YjIwZjIiLCJpYXQiOjE3NDA0ODc3MTMsImV4cCI6MTc0MDQ4NzgzM30.lg3LlQaYxHyXvHeYc3duxTxkurS6nr4dAYFYXADv8ek",
                                                "refreshToken": "bd5ae868-ed78-4b2a-a054-3465088d2c0a"
                                            }
                                            """
                            )}
                    )}),
    })
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

    @Operation(summary = "Actualiza el token de refresco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Usuario registrado",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RefreshTokenRequest.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "64ac35b8-a0c3-4352-9da9-1a8ed58b20f2",
                                                "username": "Buri",
                                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI2NGFjMzViOC1hMGMzLTQzNTItOWRhOS0xYThlZDU4YjIwZjIiLCJpYXQiOjE3NDA0ODc4MDgsImV4cCI6MTc0MDQ4NzkyOH0._HzvKdTD0r3G0dEKM3eaGmbrMRJhKt63RISDaVNeRO0",
                                                "refreshToken": "8d71fc6e-a3ce-43aa-8324-2637fc6f9863"
                                            }
                                            """
                            )}
                    )}),
    })
    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        String token = req.refreshToken();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(refreshTokenService.refreshToken(token));

    }

    @Operation(summary = "Activa una cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Cuenta activada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ActivateAccountRequest.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "64ac35b8-a0c3-4352-9da9-1a8ed58b20f2",
                                                "username": "Buri"
                                            }
                                            """
                            )}
                    )}),
    })
    @PostMapping("/activate/account")
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
    public ResponseEntity<GetUsuarioClipDto> seguirUsuario(@AuthenticationPrincipal Usuario usuario, @RequestBody EditSeguidoDto seguidoDto) {

        Usuario seguido = usuarioService.seguir(usuario, seguidoDto);


        return ResponseEntity.status(HttpStatus.CREATED).body(
                GetUsuarioClipDto.of(seguido));
    }

    @Operation(summary = "Dejar de seguir un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha dejado de seguir al usuario",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content)
    })
    @DeleteMapping("/dejar-de-seguir/{id}")
    public ResponseEntity<?> dejarDeSeguirUsuario(@AuthenticationPrincipal Usuario usuario, @PathVariable UUID id) {

        usuarioService.dejarDeSeguir(usuario, id);

        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Eliminar un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminado el usuario",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun usuario con id 11111111-1111-1111-1111-111111111111",
                    content = @Content)
    })
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable UUID id) {

        usuarioService.eliminarUsario(id);

        return ResponseEntity.noContent().build();

    }

}




