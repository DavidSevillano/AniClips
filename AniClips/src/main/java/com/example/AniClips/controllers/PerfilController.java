package com.example.AniClips.controllers;

import com.example.AniClips.dto.clip.EditClipDto;
import com.example.AniClips.dto.clip.GetClipDto;
import com.example.AniClips.dto.perfil.*;
import com.example.AniClips.files.service.StorageService;
import com.example.AniClips.files.utils.TikaMimeTypeDetector;
import com.example.AniClips.model.Clip;
import com.example.AniClips.model.Perfil;
import com.example.AniClips.model.Usuario;
import com.example.AniClips.service.PerfilService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/perfil/")
@Tag(name = "perfil", description = "Controlador perfil")
public class PerfilController {

    private final PerfilService perfilService;
    private final StorageService storageService;
    private final TikaMimeTypeDetector mimeTypeDetector;

    @Operation(summary = "Obtiene el perfil personal completo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el perfil personal",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPerfilCompletoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                  "username": "naruto",
                                                  "foto": "https://example.com/naruto",
                                                  "numeroClips": 2,
                                                  "numeroSeguidores": 1,
                                                  "numeroSeguidos": 1,
                                                  "descripcion": "Soy Naruto Uzumaki, futuro Hokage!",
                                                  "clips": [
                                                      {
                                                          "duracion": 120,
                                                          "miniatura": "https://example.com/naruto-vs-pain.jpg",
                                                          "nombreAnime": "Naruto Shippuden"
                                                      },
                                                      {
                                                          "duracion": 20,
                                                          "miniatura": "https://example.com/naruto-vs-pepe.jpg",
                                                          "nombreAnime": "Naruto Shippuden"
                                                      }
                                                  ]
                                              }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No existe ningun usuario con id 11111111-1111-1111-1111-111111111111",
                    content = @Content),
    })
    @GetMapping()
    public GetPerfilCompletoDto getById(@AuthenticationPrincipal Usuario usuario) {

        Perfil perfil = perfilService.findById(usuario);

        return GetPerfilCompletoDto.of(perfil);

    }

    @Operation(summary = "Añade una descripcion al perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Descripcion añadida",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPerfilDescripcionDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "descripcion": "nueva descripcion"
                                            }
                                            """
                            )}
                    )}),
    })
    @PostMapping("/descripcion/")
    public ResponseEntity<GetPerfilDescripcionDto> createDescripcion(@AuthenticationPrincipal Usuario usuario,
                                                                     @RequestBody EditPerfilDescripcionDto nuevo) {

        Perfil perfil = perfilService.saveDescripcion(usuario, nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(GetPerfilDescripcionDto.of(perfil));
    }

    @Operation(summary = "Edita la descripcion del perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Descripcion editada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPerfilDescripcionDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "descripcion": "nueva descripcion"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No existe ningun usuario con id 11111111-1111-1111-1111-111111111111",
                    content = @Content),
    })
    @PutMapping("/descripcion/edit")
    public GetPerfilDescripcionDto editDescripcion(@AuthenticationPrincipal Usuario usuario,
                                                   @RequestBody EditPerfilDescripcionDto editPerfilDescripcionDto) {
        Perfil perfil = perfilService.editDescripcion(usuario, editPerfilDescripcionDto);

        return GetPerfilDescripcionDto.of(perfil);
    }

    @Operation(summary = "Añade una foto de perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Foto de perfil añadida",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPerfilAvatarDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "avatar": "http://localhost:8080/clip/download/ejemplo_069451.jpg"
                                            }
                                            """
                            )}
                    )}),
    })
    @PostMapping("/foto/")
    public ResponseEntity<GetPerfilAvatarDto> createPerfil(@AuthenticationPrincipal Usuario usuario,
                                                  @Valid @ModelAttribute EditPerfilAvatarDto nuevo) {
        Perfil perfil = perfilService.saveAvatar(usuario, nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(GetPerfilAvatarDto.of(perfil));
    }

    @Operation(summary = "Edita la foto de perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Foto editada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPerfilAvatarDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "avatar": "http://localhost:8080/clip/download/ejemplo_069451.jpg"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No existe ningun usuario con id 11111111-1111-1111-1111-111111111111",
                    content = @Content),
    })
    @PutMapping("/foto/")
    public GetPerfilAvatarDto updatePerfilAvatar(
            @AuthenticationPrincipal Usuario usuario,
            @Valid @ModelAttribute EditPerfilAvatarDto nuevo) {

        Perfil perfil = perfilService.editAvatar(usuario, nuevo);
        return GetPerfilAvatarDto.of(perfil);
    }

    @GetMapping("/download/{id:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String id) {
        Resource resource = storageService.loadAsResource(id);
        String mimeType = mimeTypeDetector.getMimeType(resource);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", mimeType)
                .body(resource);
    }
}
