package com.example.AniClips.security.security.jwt.refresh;

import com.example.AniClips.security.user.model.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.Instant;
import java.util.UUID;
import lombok.Generated;
import org.hibernate.annotations.NaturalId;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    private UUID id;
    @OneToOne
    @JoinColumn(
            name = "user_id"
    )
    private Usuario usuario;
    @NaturalId
    @Column(
            nullable = false,
            unique = true
    )
    private String token;
    @Column(
            nullable = false
    )
    private Instant expireAt;
    private Instant createdAt;

    @Generated
    private static Instant $default$createdAt() {
        return Instant.now();
    }

    @Generated
    public static RefreshTokenBuilder builder() {
        return new RefreshTokenBuilder();
    }

    @Generated
    public RefreshToken() {
        this.createdAt = $default$createdAt();
    }

    @Generated
    public RefreshToken(final UUID id, final Usuario usuario, final String token, final Instant expireAt, final Instant createdAt) {
        this.id = id;
        this.usuario = usuario;
        this.token = token;
        this.expireAt = expireAt;
        this.createdAt = createdAt;
    }

    @Generated
    public UUID getId() {
        return this.id;
    }

    @Generated
    public Usuario getUser() {
        return this.usuario;
    }

    @Generated
    public String getToken() {
        return this.token;
    }

    @Generated
    public Instant getExpireAt() {
        return this.expireAt;
    }

    @Generated
    public Instant getCreatedAt() {
        return this.createdAt;
    }

    @Generated
    public void setId(final UUID id) {
        this.id = id;
    }

    @Generated
    public void setUser(final Usuario usuario) {
        this.usuario = usuario;
    }

    @Generated
    public void setToken(final String token) {
        this.token = token;
    }

    @Generated
    public void setExpireAt(final Instant expireAt) {
        this.expireAt = expireAt;
    }

    @Generated
    public void setCreatedAt(final Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Generated
    public static class RefreshTokenBuilder {
        @Generated
        private UUID id;
        @Generated
        private Usuario usuario;
        @Generated
        private String token;
        @Generated
        private Instant expireAt;
        @Generated
        private boolean createdAt$set;
        @Generated
        private Instant createdAt$value;

        @Generated
        RefreshTokenBuilder() {
        }

        @Generated
        public RefreshTokenBuilder id(final UUID id) {
            this.id = id;
            return this;
        }

        @Generated
        public RefreshTokenBuilder user(final Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        @Generated
        public RefreshTokenBuilder token(final String token) {
            this.token = token;
            return this;
        }

        @Generated
        public RefreshTokenBuilder expireAt(final Instant expireAt) {
            this.expireAt = expireAt;
            return this;
        }

        @Generated
        public RefreshTokenBuilder createdAt(final Instant createdAt) {
            this.createdAt$value = createdAt;
            this.createdAt$set = true;
            return this;
        }

        @Generated
        public RefreshToken build() {
            Instant createdAt$value = this.createdAt$value;
            if (!this.createdAt$set) {
                createdAt$value = RefreshToken.$default$createdAt();
            }

            return new RefreshToken(this.id, this.usuario, this.token, this.expireAt, createdAt$value);
        }

        @Generated
        public String toString() {
            String var10000 = String.valueOf(this.id);
            return "RefreshToken.RefreshTokenBuilder(id=" + var10000 + ", user=" + String.valueOf(this.usuario) + ", token=" + this.token + ", expireAt=" + String.valueOf(this.expireAt) + ", createdAt$value=" + String.valueOf(this.createdAt$value) + ")";
        }
    }
}