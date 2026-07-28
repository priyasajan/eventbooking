package com.example.eventbooking.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;
    @NotBlank(message = "Phone number is required")
    private String phone;
    @Enumerated(EnumType.STRING)
    private Role role;
    @NotBlank(message = "Password is required")
    private String password;






}
