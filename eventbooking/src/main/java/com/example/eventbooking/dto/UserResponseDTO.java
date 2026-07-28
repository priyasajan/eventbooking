package com.example.eventbooking.dto;

import com.example.eventbooking.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
}
