package com.example.AniClips.controllers;

import com.example.AniClips.dto.meGusta.EditMeGustaDto;
import com.example.AniClips.dto.perfil.EditPerfilDescripcionDto;
import com.example.AniClips.dto.perfil.GetPerfilDto;
import com.example.AniClips.model.MeGusta;
import com.example.AniClips.model.Perfil;
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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public ResponseEntity<Perfil> create(@RequestBody EditPerfilDescripcionDto nuevo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        perfilService.save(nuevo));
    }

    @Operation(summary = "Edita un curso por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Curso editado",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetCursoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 1,
                                                    "nombre": "Segundo",
                                                    "horasEmpresa": 350,
                                                    "profesores": [
                                                        {
                                                            "id": 1,
                                                            "nombre": "Lucia",
                                                            "apellidos": "Sanchez Garcia",
                                                            "email": "lucia@gmail.com",
                                                            "telefono": 6554321
                                                        },
                                                        {
                                                            "id": 51,
                                                            "nombre": "Luis",
                                                            "apellidos": "Gómez Torres",
                                                            "email": "lgomez@gmail.com",
                                                            "telefono": 678548923
                                                        }
                                                    ],
                                                    "nombreTitulo": "Técnico Superior en Desarrollo de Aplicaciones Multiplataforma"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el curso ",
                    content = @Content),
    })
    
    @PutMapping("/{id}")
    public GetPerfilDto edit(@RequestBody EditPerfilDescripcionDto editPerfilDescripcionDto, @PathVariable UUID id) {
         GetPerfilDto perfil = GetPerfilDto.of(editPerfilDescripcionDto);

         return perfilService.edit(perfil);
    }
}
