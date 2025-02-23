package com.example.AniClips.validation.clip;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidVideoExtensionValidator implements ConstraintValidator<ValidVideoExtension, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return false;
        }

        return value.toLowerCase().endsWith(".mp4");
    }
}
