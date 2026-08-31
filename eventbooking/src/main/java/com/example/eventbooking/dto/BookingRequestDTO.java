package com.example.eventbooking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class BookingRequestDTO {

    @NotNull(message = "User id is required")
    @Positive(message = "User id must be greater than 0")
    private Long userId;

    @NotNull(message = "Event id is required")
    @Positive(message = "Event id must be greater than 0")
    private Long eventId;

    @NotNull(message = "Number of tickets is required")
    @Positive(message = "Number of tickets must be greater than 0")
    private Integer numberOfTickets;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }


    public Integer getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(Integer numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}