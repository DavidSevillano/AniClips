package com.example.AniClips.controllers;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.dto.clip.GetClipDto;
import com.example.AniClips.dto.clip.GetClipMiniaturaDto;
import com.example.AniClips.model.Clip;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.service.ClipService;
import com.example.AniClips.util.SearchCriteria;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Validated
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
    @GetMapping("/{id}")
    public GetClipDto getById(@PathVariable Long id) {

        Clip clip = clipService.findById(id);

        return GetClipDto.of(clip);

    }

    @Operation(summary = "Obtiene todos los clips por nombre o filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado clips por nombre o filtro",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetClipMiniaturaDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                  {
                                                      "duracion": 140,
                                                      "miniatura": "https://example.com/eren-vs-reiner",
                                                      "nombreAnime": "Attack on Titan"
                                                  },
                                                  {
                                                      "duracion": 240,
                                                      "miniatura": "https://example.com/eren-vs-bertorto",
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
    @GetMapping("/buscar/")
    public List<GetClipMiniaturaDto> buscar(@RequestParam(value="search", required = true) String search) {
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(.+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return clipService.search(params)
                .stream()
                .map(GetClipMiniaturaDto::of)
                .toList();
    }

    @Operation(summary = "Añade un clip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Clip añadido",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EditClipDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "id": 7,
                                                 "nombreAnime": "Kimetsu no Yaiba",
                                                 "genero": "Shonen",
                                                 "descripcion": "piumpium",
                                                 "urlVideo": "www.clip.mp4",
                                                 "fecha": "2025-02-23",
                                                 "visitas": 0,
                                                 "duracion": 0,
                                                 "miniatura": "www.miniatura.jpg",
                                                 "meGustas": [],
                                                 "valoraciones": [],
                                                 "comentarios": []
                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun clip",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<Clip> create(@AuthenticationPrincipal Usuario usuario, @Valid @RequestBody EditClipDto nuevo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        clipService.save(usuario, nuevo));
    }

}


