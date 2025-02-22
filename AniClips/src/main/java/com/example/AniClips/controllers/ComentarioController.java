package com.example.AniClips.controllers;

import com.example.AniClips.dto.clip.GetClipDto;
import com.example.AniClips.dto.comentario.GetComentarioDto;
import com.example.AniClips.service.ClipService;
import com.example.AniClips.service.ComentarioService;
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
@RequestMapping("/comentario/")
@Tag(name = "comentario", description = "Controlador comentario")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @Operation(summary = "Obtiene todos los comentario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado comentarios",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetComentarioDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                  {
                                                      "texto": "¡Esta batalla fue épica!",
                                                      "fecha": "2023-01-16",
                                                      "getUsuarioClipDto": {
                                                          "Username": "goku",
                                                          "getPerfilAvatarDto": {
                                                              "avatar": "https://example.com/goku"
                                                          }
                                                      }
                                                  },
                                                  {
                                                      "texto": "¡Siempre me emociona esta escena!",
                                                      "fecha": "2023-02-21",
                                                      "getUsuarioClipDto": {
                                                          "Username": "eren",
                                                          "getPerfilAvatarDto": {
                                                              "avatar": "https://example.com/eren"
                                                          }
                                                      }
                                                  },
                                                  {
                                                      "texto": "No hay mejor pelea en AOT.",
                                                      "fecha": "2023-03-11",
                                                      "getUsuarioClipDto": {
                                                          "Username": "naruto",
                                                          "getPerfilAvatarDto": {
                                                              "avatar": "https://example.com/naruto"
                                                          }
                                                      }
                                                  }
                                              ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningún comentario",
                    content = @Content),
    })
    @GetMapping
    public List<GetComentarioDto> getAll() {
        return comentarioService.findAll()
                .stream()
                .map(GetComentarioDto::of)
                .toList();
    }
}
