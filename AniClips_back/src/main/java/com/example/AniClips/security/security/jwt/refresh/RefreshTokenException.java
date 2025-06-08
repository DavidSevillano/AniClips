package com.example.AniClips.security.security.jwt.refresh;

import com.example.AniClips.security.security.exceptionhandling.JwtException;

public class RefreshTokenException extends JwtException {
    public RefreshTokenException(String s) {
        super(s);
    }
}