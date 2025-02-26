package com.example.AniClips.error;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {

      super(message);
    }
}
