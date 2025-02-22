package com.example.AniClips.controllers;

import com.example.AniClips.dto.Valoracion.EditValoracionDto;
import com.example.AniClips.dto.meGusta.EditMeGustaDto;
import com.example.AniClips.model.MeGusta;
import com.example.AniClips.model.Valoracion;
import com.example.AniClips.service.MeGustaService;
import com.example.AniClips.service.ValoracionService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/valoracion/")
@Tag(name = "valoracion", description = "Controlador valoracion")
public class ValoracionController {

    final ValoracionService valoracionService;

    @Operation(summary = "Añade una valoracion")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Valoracion añadida",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EditValoracionDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": 4,
                                                "puntuacion": 5.0,
                                                "fecha": "2025-02-22"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna valoracion",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<Valoracion> create(@RequestBody EditValoracionDto nuevo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        valoracionService.save(nuevo));
    }

}
