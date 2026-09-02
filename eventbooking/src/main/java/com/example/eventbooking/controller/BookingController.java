
        package com.example.eventbooking.controller;

import com.example.eventbooking.dto.BookingRequestDTO;
import com.example.eventbooking.dto.BookingResponseDTO;
import com.example.eventbooking.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/bookings")
@SecurityRequirement(name = "bearer-key")
public class BookingController {

    @Autowired
    private BookingService bookingService;





    @Operation(
            summary = "Create a booking",
            description = "User can create a booking for an event.",
            tags = {"User"}
    )
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO request) {

        return ResponseEntity.ok(
                bookingService.saveBooking(request)
        );
    }




    @Operation(
            summary = "Get booking by ID",
            description = "User can view booking details.",
            tags = {"User"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }


    //get all bookings
    //admin

    @Operation(
            summary = "Get all bookings",
            description = "Admin can view all bookings in the system.",
            tags = {"Admin"}
    )
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }


   //cancel booking

    @Operation(
            summary = "Cancel booking",
            description = "User can cancel their booking.",
            tags = {"User"}
    )
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }
}

