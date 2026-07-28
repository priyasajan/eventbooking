package com.example.eventbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestDTO {
    @NotBlank(message = "Event name is required")
    private String eventName;
    private String description;
    @NotBlank(message= "Venue is required")
    private String venue;
    @NotNull(message ="Ticket price is required") @Positive(message="Ticket price must be greater than 0")
    private Double ticketPrice;
    @NotNull(message = "total seat is required..") @Positive(message="total seats must be greater than 0")
    private Integer totalSeats;

}
