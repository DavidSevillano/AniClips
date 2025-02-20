package com.example.AniClips.security.security.jwt.access;

import com.example.AniClips.security.security.exceptionhandling.JwtException;
import com.example.AniClips.security.user.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    public static final String TOKEN_TYPE = "JWT";
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.duration}")
    private long jwtLifeInMinutes;
    private JwtParser jwtParser;
    private SecretKey secretKey;

    public JwtService() {
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(this.jwtSecret.getBytes());
        this.jwtParser = Jwts.parser().verifyWith(this.secretKey).build();
    }

    public String generateAccessToken(Usuario usuario) {
        Date tokeExpirationDate = Date.from(LocalDateTime.now().plusMinutes(this.jwtLifeInMinutes).atZone(ZoneId.systemDefault()).toInstant());
        return ((JwtBuilder)((JwtBuilder.BuilderHeader)Jwts.builder().header().type("JWT")).and()).subject(usuario.getId().toString()).issuedAt(new Date()).expiration(tokeExpirationDate).signWith(this.secretKey).compact();
    }

    public UUID getUserIdFromAccessToken(String token) {
        String sub = ((Claims)this.jwtParser.parseClaimsJws(token).getBody()).getSubject();
        return UUID.fromString(sub);
    }

    public boolean validateAccessToken(String token) {
        try {
            this.jwtParser.parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException | SignatureException ex) {
            throw new JwtException(((RuntimeException)ex).getMessage());
        }
    }
}
