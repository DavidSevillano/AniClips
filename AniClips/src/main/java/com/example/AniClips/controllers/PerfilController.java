package com.example.AniClips.controllers;

import com.example.AniClips.dto.perfil.EditPerfilDescripcionDto;
import com.example.AniClips.model.MeGusta;
import com.example.AniClips.model.Perfil;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perfil/")
@Tag(name = "perfil", description = "Controlador perfil")
public class PerfilController {

    private final PerfilService perfilService;

    @Operation(summary = "Añade una descripcion al perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Descripcion añadida",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EditPerfilDescripcionDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 3,
                                                "avatar": null,
                                                "descripcion": "nueva descripcion"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún perfil",
                    content = @Content),
    })
    @PostMapping("/descripcion/")
    public ResponseEntity<Perfil> create(@AuthenticationPrincipal Usuario usuario, @RequestBody EditPerfilDescripcionDto nuevo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        perfilService.save(usuario, nuevo));
    }
}
