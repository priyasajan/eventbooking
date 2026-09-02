package com.example.eventbooking.controller;

import com.example.eventbooking.dto.UserRequestDTO;
import com.example.eventbooking.dto.UserResponseDTO;
import com.example.eventbooking.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/User")
public class UserController {

    @Autowired
    private UserService userService;


// create user

    @Operation(
            summary = "Create a user",
            description = "Admin can create a new user.",
            tags = {"Admin"}
    )
    @PostMapping
    public UserResponseDTO save(
            @Valid @RequestBody UserRequestDTO request) {

        return userService.saveUser(request);
    }



    @Operation(
            summary = "Get all users",
            description = "Admin can view all registered users.",
            tags = {"Admin"}
    )
    @GetMapping
    public List<UserResponseDTO> getAllUsers(
            @RequestParam int page,
            @RequestParam int size) {

        return userService.getAllUsers(page, size);
    }


//get user by email

    @Operation(
            summary = "Get user by email",
            description = "Get user details using email.",
            tags = {"User"}
    )
    @GetMapping("/email/{email}")
    public UserResponseDTO getUserByEmail(
            @PathVariable String email) {

        return userService.getUserByEmail(email);
    }


//get user by id

    @Operation(
            summary = "Get user by ID",
            description = "Admin can view details of a specific user.",
            tags = {"Admin"}
    )
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(
            @PathVariable Long id) {

        return userService.getUserById(id);
    }


  //update

    @Operation(
            summary = "Update user",
            description = "Admin can update user details.",
            tags = {"Admin"}
    )
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO request) {

        return userService.updateUser(id, request);
    }


   //delete

    @Operation(
            summary = "Delete user",
            description = "Admin can delete a user.",
            tags = {"Admin"}
    )
    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);
    }
}