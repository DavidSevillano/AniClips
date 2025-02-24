package com.example.AniClips.controllers;

import com.example.AniClips.dto.clip.EditClipDto;
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
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún perfil",
                    content = @Content),
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
                            array = @ArraySchema(schema = @Schema(implementation = GetPerfilDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "avatar": "https://example.com/naruto",
                                                 "descripcion": "nueva descripcion"
                                             }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el perfil ",
                    content = @Content),
    })
    @PutMapping("/descripcion/edit")
    public GetPerfilDto editDescripcion(@AuthenticationPrincipal Usuario usuario, @RequestBody EditPerfilDescripcionDto editPerfilDescripcionDto) {
        Perfil perfil = perfilService.editDescripcion(usuario, editPerfilDescripcionDto);

        return GetPerfilDto.of(perfil);
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
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna foto de perfil",
                    content = @Content),
    })
    @PostMapping("/foto/")
    public ResponseEntity<GetPerfilAvatarDto> createPerfil(@AuthenticationPrincipal Usuario usuario,
                                                  @Valid @ModelAttribute EditPerfilAvatarDto nuevo) {
        Perfil perfil = perfilService.saveAvatar(usuario, nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(GetPerfilAvatarDto.of(perfil));
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
