package com.example.AniClips.validation.clip;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidVideoExtensionValidator.class)
public @interface ValidVideoExtension {
    String message() default "El archivo de video debe ser .mp4";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
