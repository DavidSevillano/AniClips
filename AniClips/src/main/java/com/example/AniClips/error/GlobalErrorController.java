package com.example.AniClips.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class GlobalErrorController
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClipNotFoundException.class)
    public ProblemDetail handleContactoNotFound(ClipNotFoundException ex) {
        ProblemDetail result = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND,
                        ex.getMessage());
        result.setTitle("Clip no encontrado");
        result.setType(URI.create("https://www.salesianos-triana.edu/errors/clip-not-found"));
        result.setProperty("author", "David");

        return result;

    }

}
