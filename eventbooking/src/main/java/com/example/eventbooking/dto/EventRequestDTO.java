package com.example.eventbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {

    @NotBlank(message = "Event name is required")
    private String eventName;

    private String description;

    @NotBlank(message = "Venue is required")
    private String venue;

    @NotNull(message = "Ticket price is required")
    @Positive(message = "Ticket price must be greater than 0")
    private Double ticketPrice;

    @NotNull(message = "Event date a is required")
    private LocalDate eventDate;
    @NotNull(message = "Event time is required")
    private LocalTime eventTime;

    @NotNull(message = "Total seat is required")
    @Positive(message = "Total seats must be greater than 0")
    private Integer totalSeats;
}