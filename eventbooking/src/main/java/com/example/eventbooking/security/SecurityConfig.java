package com.example.eventbooking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // =========================
                        // SWAGGER
                        // =========================
                        .requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()


                        // =========================
                        // REGISTER & LOGIN
                        // =========================
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll()


                        // =========================
                        // EVENTS - GET
                        // USER, ORGANIZER, ADMIN
                        // =========================
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/events/**"
                        ).authenticated()


                        // =========================
                        // EVENTS - CREATE
                        // ORGANIZER & ADMIN
                        // =========================
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/events/**"
                        ).hasAnyRole("ORGANIZER", "ADMIN")


                        // =========================
                        // EVENTS - UPDATE
                        // ORGANIZER & ADMIN
                        // =========================
                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/events/**"
                        ).hasAnyRole("ORGANIZER", "ADMIN")


                        // =========================
                        // EVENTS - DELETE
                        // ORGANIZER & ADMIN
                        // =========================
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/events/**"
                        ).hasAnyRole("ORGANIZER", "ADMIN")


                        // =========================
                        // BOOKINGS
                        // =========================
                        .requestMatchers(
                                "/api/bookings/**"
                        ).authenticated()


                        // =========================
                        // EVERYTHING ELSE
                        // =========================
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }


    // =========================
    // PASSWORD ENCODER
    // =========================

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // =========================
    // AUTHENTICATION MANAGER
    // =========================

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }
}