package com.example.eventbooking.controller;

import com.example.eventbooking.dto.BookingRequestDTO;
import com.example.eventbooking.dto.BookingResponseDTO;
import com.example.eventbooking.service.BookingService;

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


    // CREATE BOOKING
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(
            @Valid @RequestBody BookingRequestDTO request) {

        return ResponseEntity.ok(
                bookingService.saveBooking(request)
        );
    }


    // GET BOOKING BY ID
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.getBookingById(id)
        );
    }


    // GET ALL BOOKINGS
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponseDTO>> getAllBookings() {

        return ResponseEntity.ok(
                bookingService.getAllBookings()
        );
    }


    // CANCEL BOOKING
    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponseDTO> cancelBooking(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bookingService.cancelBooking(id)
        );
    }
}