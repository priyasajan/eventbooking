package com.example.eventbooking.controller;

import com.example.eventbooking.dto.LoginRequestDTO;
import com.example.eventbooking.dto.LoginResponseDTO;
import com.example.eventbooking.dto.UserRequestDTO;
import com.example.eventbooking.dto.UserResponseDTO;
import com.example.eventbooking.entity.User;
import com.example.eventbooking.repository.UserRepository;
import com.example.eventbooking.security.JwtService;
import com.example.eventbooking.service.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Authentication",
        description = "Registration and Login APIs"
)
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserService userService;

    private final UserRepository userRepository;


    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserService userService,
            UserRepository userRepository) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userRepository = userRepository;
    }



    // REGISTER

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(
            @Valid @RequestBody UserRequestDTO request) {

        UserResponseDTO response =
                userService.saveUser(request);

        return ResponseEntity.ok(response);
    }



    // LOGIN


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        // Find user from database
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        // Get user's role from database
        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );


        LoginResponseDTO response =
                new LoginResponseDTO(
                        "Login successful",
                        user.getEmail(),
                        token
                );


        return ResponseEntity.ok(response);
    }
}