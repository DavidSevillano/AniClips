package com.example.AniClips.security.security.exceptionhandling;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class JwtControllerAdvice extends ResponseEntityExceptionHandler {
    public JwtControllerAdvice() {
    }

    @ExceptionHandler({AuthenticationException.class})
    public ProblemDetail handleAuthenticationException(AuthenticationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler({JwtException.class})
    public ProblemDetail handleJwtException(JwtException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, ex.getMessage());
        return problemDetail;
    }
}
