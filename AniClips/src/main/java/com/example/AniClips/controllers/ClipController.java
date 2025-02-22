package com.example.AniClips.controllers;

import com.example.AniClips.dto.clip.GetClipDto;
import com.example.AniClips.dto.clip.GetClipMiniaturaDto;
import com.example.AniClips.service.ClipService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clip/")
@Tag(name = "clips", description = "Controlador clip")
public class ClipController {

    private final ClipService clipService;

    @Operation(summary = "Obtiene todos los clips")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado clips",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetClipDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                 {
                                                     "descripcion": "Naruto vs Pain",
                                                     "urlVideo": "https://example.com/naruto-vs-pain",
                                                     "fecha": "2023-01-15",
                                                     "visitas": 5000,
                                                     "duracion": 120,
                                                     "getUsuarioClipDto": {
                                                         "Username": "naruto",
                                                         "getPerfilAvatarDto": {
                                                             "avatar": "https://example.com/naruto"
                                                         }
                                                     },
                                                     "cantidadMeGusta": 1,
                                                     "cantidadComentarios": 1,
                                                     "mediaValoraciones": 9.5
                                                 },
                                                 {
                                                     "descripcion": "Goku se transforma en Super Saiyan",
                                                     "urlVideo": "https://example.com/goku-ssj",
                                                     "fecha": "2023-02-20",
                                                     "visitas": 10000,
                                                     "duracion": 150,
                                                     "getUsuarioClipDto": {
                                                         "Username": "goku",
                                                         "getPerfilAvatarDto": {
                                                             "avatar": "https://example.com/goku"
                                                         }
                                                     },
                                                     "cantidadMeGusta": 1,
                                                     "cantidadComentarios": 1,
                                                     "mediaValoraciones": 10.0
                                                 },
                                                 {
                                                     "descripcion": "Eren vs Reiner",
                                                     "urlVideo": "https://example.com/eren-vs-reiner",
                                                     "fecha": "2023-03-10",
                                                     "visitas": 8000,
                                                     "duracion": 140,
                                                     "getUsuarioClipDto": {
                                                         "Username": "eren",
                                                         "getPerfilAvatarDto": {
                                                             "avatar": "https://example.com/eren"
                                                         }
                                                     },
                                                     "cantidadMeGusta": 1,
                                                     "cantidadComentarios": 1,
                                                     "mediaValoraciones": 8.5
                                                 }
                                             ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún clip",
                    content = @Content),
    })
    @GetMapping
    public List<GetClipDto> getAll() {
        return clipService.findAll()
                .stream()
                .map(GetClipDto::of)
                .toList();
    }

    @Operation(summary = "Obtiene todos los clips")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado clips",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetClipMiniaturaDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "duracion": 120,
                                                    "miniatura": "https://example.com/naruto-vs-pain.jpg",
                                                    "nombreAnime": "Naruto Shippuden"
                                                },
                                                {
                                                    "duracion": 150,
                                                    "miniatura": "https://example.com/goku-ssj.jpg",
                                                    "nombreAnime": "Dragon Ball Z"
                                                },
                                                {
                                                    "duracion": 140,
                                                    "miniatura": "https://example.com/eren-vs-reiner",
                                                    "nombreAnime": "Attack on Titan"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún clip",
                    content = @Content),
    })
    @GetMapping("/miniatura")
    public List<GetClipMiniaturaDto> getAllMiniatura() {
        return clipService.findAll()
                .stream()
                .map(GetClipMiniaturaDto::of)
                .toList();
    }
}
