package com.example.AniClips.validation.clip;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidMiniaturaExtensionValidator implements ConstraintValidator<ValidMiniaturaExtension, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return false;
        }

        return value.toLowerCase().endsWith(".jpg");
    }
}
