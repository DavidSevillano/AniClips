package com.example.AniClips.controllers;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.dto.clip.GetClipDto;
import com.example.AniClips.dto.clip.GetClipMiniaturaDto;
import com.example.AniClips.files.service.StorageService;
import com.example.AniClips.files.utils.TikaMimeTypeDetector;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Usuario;
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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/clip/")
@Tag(name = "clip", description = "Controlador clip")
public class ClipController {

    private final ClipService clipService;
    private final StorageService storageService;
    private final TikaMimeTypeDetector mimeTypeDetector;


    @Operation(summary = "Obtiene todos los clips")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado clips",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetClipDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                  "content": [
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
                                                      }
                                                  ],
                                                  "pageable": {
                                                      "pageNumber": 0,
                                                      "pageSize": 1,
                                                      "sort": {
                                                          "empty": true,
                                                          "sorted": false,
                                                          "unsorted": true
                                                      },
                                                      "offset": 0,
                                                      "paged": true,
                                                      "unpaged": false
                                                  },
                                                  "last": false,
                                                  "totalElements": 4,
                                                  "totalPages": 4,
                                                  "first": true,
                                                  "size": 1,
                                                  "number": 0,
                                                  "sort": {
                                                      "empty": true,
                                                      "sorted": false,
                                                      "unsorted": true
                                                  },
                                                  "numberOfElements": 1,
                                                  "empty": false
                                              }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado clips",
                    content = @Content),
    })
    @GetMapping
    public Page<GetClipDto> getAll(@RequestParam(defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, 1);
        return clipService.findAll(pageRequest)
                .map(GetClipDto::of);
    }   

    @Operation(summary = "Obtiene todos los clips")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado clips",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetClipMiniaturaDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "content": [
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
                                                        "duracion": 130,
                                                        "miniatura": "https://example.com/luffy-vs-crocodile.jpg",
                                                        "nombreAnime": "One Piece"
                                                    },
                                                    {
                                                        "duracion": 180,
                                                        "miniatura": "https://example.com/zoro-vs-mihawk.jpg",
                                                        "nombreAnime": "One Piece"
                                                    }
                                                ],
                                                "pageable": {
                                                    "pageNumber": 0,
                                                    "pageSize": 15,
                                                    "sort": {
                                                        "empty": true,
                                                        "sorted": false,
                                                        "unsorted": true
                                                    },
                                                    "offset": 0,
                                                    "paged": true,
                                                    "unpaged": false
                                                },
                                                "last": true,
                                                "totalElements": 4,
                                                "totalPages": 1,
                                                "first": true,
                                                "size": 15,
                                                "number": 0,
                                                "sort": {
                                                    "empty": true,
                                                    "sorted": false,
                                                    "unsorted": true
                                                },
                                                "numberOfElements": 4,
                                                "empty": false
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún clip",
                    content = @Content),
    })
    @GetMapping("/miniatura")
    public Page<GetClipMiniaturaDto> getAllMiniatura(@RequestParam(defaultValue = "0") int page) {
        Pageable pageRequest = PageRequest.of(page, 15);
        return clipService.findAll(pageRequest)
                .map(GetClipMiniaturaDto::of);
    }

    @Operation(summary = "Obtiene un clip por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha el clip",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetClipDto.class)),
                            examples = {@ExampleObject(
                                    value = """
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
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No existe ningun clip con id 1",
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
                                            {
                                                "content": [
                                                    {
                                                        "duracion": 130,
                                                        "miniatura": "https://example.com/luffy-vs-crocodile.jpg",
                                                        "nombreAnime": "One Piece"
                                                    },
                                                    {
                                                        "duracion": 180,
                                                        "miniatura": "https://example.com/zoro-vs-mihawk.jpg",
                                                        "nombreAnime": "One Piece"
                                                    }
                                                ],
                                                "pageable": {
                                                    "pageNumber": 0,
                                                    "pageSize": 15,
                                                    "sort": {
                                                        "empty": true,
                                                        "sorted": false,
                                                        "unsorted": true
                                                    },
                                                    "offset": 0,
                                                    "paged": true,
                                                    "unpaged": false
                                                },
                                                "last": true,
                                                "totalElements": 2,
                                                "totalPages": 1,
                                                "first": true,
                                                "size": 15,
                                                "number": 0,
                                                "sort": {
                                                    "empty": true,
                                                    "sorted": false,
                                                    "unsorted": true
                                                },
                                                "numberOfElements": 2,
                                                "empty": false
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se encontraron clips con los criterios especificados.",
                    content = @Content),
    })
    @GetMapping("/buscar/")
    public Page<GetClipMiniaturaDto> buscar(@RequestParam(value = "search", required = true) String search,
                                            @RequestParam(defaultValue = "0") int page) {

        Pageable pageRequest = PageRequest.of(page, 15);

        List<SearchCriteria> params = new ArrayList<>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(.+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }

        Page<Clip> result = clipService.search(params, pageRequest);

        return result.map(GetClipMiniaturaDto::of);
    }

    @Operation(summary = "Añade un clip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Clip añadido",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Clip.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                  "id": 7,
                                                  "nombreAnime": "Kimetsu no Yaiba",
                                                  "genero": "Shonen",
                                                  "descripcion": "piumpium",
                                                  "urlVideo": "http://localhost:8080/clip/download/Ejemplo_173859.mp4",
                                                  "fecha": "2025-02-24",
                                                  "visitas": 0,
                                                  "duracion": 0,
                                                  "miniatura": "http://localhost:8080/clip/download/ejemplo_173874.jpg",
                                                  "meGustas": [],
                                                  "valoraciones": [],
                                                  "comentarios": []
                                              }
                                            """
                            )}
                    )}),
    })
    @PostMapping
    public ResponseEntity<Clip> create(@AuthenticationPrincipal Usuario usuario,
                                       @Valid @ModelAttribute EditClipDto nuevo) {
        Clip clip = clipService.save(usuario, nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(clip);
    }

    @GetMapping("/download/{id:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String id) {
        Resource resource = storageService.loadAsResource(id);
        String mimeType = mimeTypeDetector.getMimeType(resource);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", mimeType)
                .body(resource);
    }

    @Operation(summary = "Eliminar un clip del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminado el clip",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permiso para eliminar este clip",
                    content = @Content)
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarMiClip(@AuthenticationPrincipal Usuario usuario, @PathVariable Long id) {

        clipService.eliminarMiClip(usuario, id);

        return ResponseEntity.noContent().build();

    }

    @Operation(summary = "Eliminar un clip como admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminado el clip",
                    content = @Content),
    })
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> eliminarMiClip(@PathVariable Long id) {

        clipService.eliminarClip(id);

        return ResponseEntity.noContent().build();

    }


}


