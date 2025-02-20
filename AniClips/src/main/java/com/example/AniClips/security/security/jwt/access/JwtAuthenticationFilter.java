package com.example.AniClips.security.security.jwt.access;

import com.example.AniClips.security.security.exceptionhandling.JwtException;
import com.example.AniClips.security.user.model.Usuario;
import com.example.AniClips.security.user.repo.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.getJwtAccessTokenFromRequest(request);

        try {
            if (StringUtils.hasText(token) && this.jwtService.validateAccessToken(token)) {
                UUID id = this.jwtService.getUserIdFromAccessToken(token);
                Optional<Usuario> result = this.userRepository.findById(id);
                if (result.isPresent()) {
                    Usuario usuario = (Usuario)result.get();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, (Object)null, usuario.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (JwtException ex) {
            this.resolver.resolveException(request, response, (Object)null, ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") ? bearerToken.substring("Bearer ".length()) : null;
    }

    @Generated
    public JwtAuthenticationFilter(final UserRepository userRepository, final JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
}

