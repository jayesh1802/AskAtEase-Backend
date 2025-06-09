package com.example.AskAtEase.Config;

import com.example.AskAtEase.authenticationProvider.JWTAuthenticationProvider;
import com.example.AskAtEase.filter.JWTAuthenticationFilter;
import com.example.AskAtEase.filter.JWTRefreshFilter;
import com.example.AskAtEase.filter.JwtValidationFilter;
import com.example.AskAtEase.util.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    public SecurityConfig(JWTUtil jwtUtil, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider() {
        return new JWTAuthenticationProvider(jwtUtil, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(
                daoAuthenticationProvider(),
                jwtAuthenticationProvider()
        ));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        // Initialize JWT filters
        JWTAuthenticationFilter jwtAuthFilter = new JWTAuthenticationFilter(authenticationManager, jwtUtil);
        JwtValidationFilter jwtValidationFilter = new JwtValidationFilter(authenticationManager);
        JWTRefreshFilter jwtRefreshFilter = new JWTRefreshFilter(jwtUtil, authenticationManager);

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register", "/generate-token", "/refresh-token").permitAll()
                        .requestMatchers("/api/users").hasRole("USER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtValidationFilter, JWTAuthenticationFilter.class)
                .addFilterAfter(jwtRefreshFilter, JwtValidationFilter.class);

        return http.build();
    }
}
