package com.example.eventbooking.controller;

import com.example.eventbooking.dto.UserRequestDTO;
import com.example.eventbooking.dto.UserResponseDTO;
import com.example.eventbooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponseDTO save(@Valid @RequestBody UserRequestDTO request){
     return userService.saveUser(request);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers(@RequestParam int page, @RequestParam int size){
        return userService.getAllUsers(page, size);
    }
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO request) {

        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        userService= null;
    }

}
