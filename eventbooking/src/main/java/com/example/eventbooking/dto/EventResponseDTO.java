package com.example.eventbooking.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


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
    private LocalDate eventDate;
    private LocalTime eventTime;
}
