package com.example.AniClips.controllers;

import com.example.AniClips.model.MeGusta;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.service.MeGustaService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meGusta/")
@Tag(name = "meGusta", description = "Controlador meGusta")
public class MeGustaController {

    final MeGustaService meGustaService;

    @Operation(summary = "Añade un me gusta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Me gusta añadido",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MeGusta.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                    "id": 4,
                                                    "fecha": "2025-02-22"
                                                    }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún clip",
                    content = @Content),
    })
    @PostMapping("/{id}")
    public ResponseEntity<MeGusta> create(@AuthenticationPrincipal Usuario usuario, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        meGustaService.save(usuario, id));
    }

}
