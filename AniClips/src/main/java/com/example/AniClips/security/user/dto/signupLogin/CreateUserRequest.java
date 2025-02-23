package com.example.AniClips.security.user.dto.signupLogin;

import com.example.AniClips.validation.user.FieldsValueMatch;
import com.example.AniClips.validation.user.UniqueUsername;
import jakarta.validation.constraints.NotBlank;

@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Los valores de password y verifyPassword no coinciden"),
})
public record CreateUserRequest(
        @UniqueUsername
        String username,

        @NotBlank(message = "El email no puede estar vacio")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String password,

        @NotBlank(message = "La verificacion de contraseña no puede estar vacía")
        String verifyPassword
) {
}
