package com.example.eventbooking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String eventName;
    private String description;
    private String venue;
    private Double ticketPrice;
    private Integer totalSeats;
}
