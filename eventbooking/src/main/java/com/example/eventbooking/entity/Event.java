package com.example.eventbooking.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;
    private String description;
    private String venue;
    private Double ticketPrice;

    private Integer totalSeats;
    private LocalDate eventDate;
    private LocalTime eventTime;

    private Integer availableSeats;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;
}