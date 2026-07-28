package com.example.eventbooking.service;

import com.example.eventbooking.dto.UserRequestDTO;
import com.example.eventbooking.dto.UserResponseDTO;
import com.example.eventbooking.entity.Role;
import com.example.eventbooking.entity.User;
import com.example.eventbooking.exception.UserAlreadyExistsException;
import com.example.eventbooking.exception.UserNotFoundException;
import com.example.eventbooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // CREATE USER

    public UserResponseDTO saveUser(UserRequestDTO request) {

        // Check whether email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with this email");
        }

        // Convert RequestDTO -> Entity
        User user = mapToEntity(request);

        // Save user into database
        User savedUser = userRepository.save(user);

        // Convert Entity -> ResponseDTO
        return mapToResponseDTO(savedUser);
    }


    // GET ALL USERS

    public List<UserResponseDTO> getAllUsers(int page, int size) {

        // Create pagination object
        Pageable pageable = PageRequest.of(page, size);

        // Fetch users from database
        Page<User> userPage = userRepository.findAll(pageable);

        // Get only current page content
        List<User> users = userPage.getContent();

        // Response list
        List<UserResponseDTO> responseList = new ArrayList<>();

        // Convert Entity -> ResponseDTO
        for (User user : users) {
            responseList.add(mapToResponseDTO(user));
        }

        return responseList;
    }


    // GET USER BY ID

    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return mapToResponseDTO(user);
    }

    // UPDATE USER

    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {

        // Check whether user exists
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Update user details
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        // Save updated user
        User updatedUser = userRepository.save(user);

        // Convert Entity -> ResponseDTO
        return mapToResponseDTO(updatedUser);
    }

    // DELETE USER


    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));

        userRepository.delete(user);
    }


    // REQUEST DTO -> ENTITY

    private User mapToEntity(UserRequestDTO request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());

        // Default role for every new user
        user.setRole(Role.USER);

        return user;
    }

    // ENTITY -> RESPONSE DTO


    private UserResponseDTO mapToResponseDTO(User user) {

        UserResponseDTO response = new UserResponseDTO();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        return response;
    }
}