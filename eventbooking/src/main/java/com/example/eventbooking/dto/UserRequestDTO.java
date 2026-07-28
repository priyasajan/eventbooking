package com.example.eventbooking.dto;

import com.example.eventbooking.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserRequestDTO {
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;
    @NotBlank(message = "Phon number is required")
    private String phone;
    @NotBlank(message = "Password is required")
    private String password;
}
