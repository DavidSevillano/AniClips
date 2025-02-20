package com.example.AniClips.security.security;

import com.example.AniClips.security.security.exceptionhandling.JwtAccessDeniedHandler;
import com.example.AniClips.security.security.exceptionhandling.JwtAuthenticationEntryPoint;
import com.example.AniClips.security.security.jwt.access.JwtAuthenticationFilter;
import lombok.Generated;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = (AuthenticationManagerBuilder)http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager authenticationManager = (AuthenticationManager)authenticationManagerBuilder.authenticationProvider(this.authenticationProvider()).build();
        return authenticationManager;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(this.userDetailsService);
        p.setPasswordEncoder(this.passwordEncoder);
        return p;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling((excepz) -> excepz.authenticationEntryPoint(this.authenticationEntryPoint).accessDeniedHandler(this.accessDeniedHandler));
        http.authorizeHttpRequests((authz) -> ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authz.requestMatchers(HttpMethod.POST, new String[]{"/auth/register", "/auth/login"})).permitAll().requestMatchers(new String[]{"/me/admin"})).hasRole("ADMIN").anyRequest()).authenticated());
        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return (SecurityFilterChain)http.build();
    }

    @Generated
    public SecurityConfig(final PasswordEncoder passwordEncoder, final UserDetailsService userDetailsService, final JwtAuthenticationFilter jwtAuthenticationFilter, final JwtAuthenticationEntryPoint authenticationEntryPoint, final JwtAccessDeniedHandler accessDeniedHandler) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }
}
