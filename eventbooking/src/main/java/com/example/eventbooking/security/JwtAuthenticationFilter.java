package com.example.eventbooking.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("=================================");
        System.out.println("Request URL: " + request.getRequestURI());
        System.out.println("Request Method: " + request.getMethod());

        // Get Authorization header
        String authHeader = request.getHeader("Authorization");

        System.out.println("AUTH HEADER = " + authHeader);

        // Check Bearer token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            System.out.println("NO BEARER TOKEN FOUND");

            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer "
        String jwt = authHeader.substring(7).trim();

        if (jwt.isEmpty()) {

            System.out.println("BEARER TOKEN IS EMPTY");

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("JWT RECEIVED");

        try {

            // Validate JWT
            if (!jwtService.isTokenValid(jwt)) {

                System.out.println("JWT IS INVALID");

                filterChain.doFilter(request, response);
                return;
            }

            System.out.println("JWT IS VALID");

            // Extract email
            String email = jwtService.extractEmail(jwt);

            System.out.println("User email: " + email);

            // Load user from database
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(email);

            // Create authentication
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

            // Create fresh SecurityContext
            SecurityContext context =
                    SecurityContextHolder.createEmptyContext();

            context.setAuthentication(authentication);

            // Set context
            SecurityContextHolder.setContext(context);

            System.out.println(
                    "Authentication set successfully"
            );

            System.out.println(
                    "Authorities: "
                            + userDetails.getAuthorities()
            );

            System.out.println(
                    "Authenticated: "
                            + SecurityContextHolder
                            .getContext()
                            .getAuthentication()
                            .isAuthenticated()
            );

        } catch (Exception e) {

            System.out.println("JWT ERROR");
            System.out.println(
                    "Error Type: "
                            + e.getClass().getName()
            );
            System.out.println(
                    "Error Message: "
                            + e.getMessage()
            );
        }

        System.out.println("=================================");

        // Continue request
        filterChain.doFilter(request, response);
    }
}