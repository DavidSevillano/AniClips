package com.example.AniClips.error;

public class ActivationExpiredException extends RuntimeException {
    public ActivationExpiredException(String s) {
        super(s);
    }
}