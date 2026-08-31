package com.example.eventbooking.controller;

import com.example.eventbooking.dto.EventRequestDTO;
import com.example.eventbooking.dto.EventResponseDTO;
import com.example.eventbooking.service.EventService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;


    // CREATE
    @PostMapping
    public ResponseEntity<EventResponseDTO> saveEvent(
            @Valid @RequestBody EventRequestDTO request) {

        EventResponseDTO response =
                eventService.saveEvent(request);

        return ResponseEntity.ok(response);
    }


    // GET ALL
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents(
            @RequestParam int page,
            @RequestParam int size) {

        List<EventResponseDTO> events =
                eventService.getAllEvents(page, size);

        return ResponseEntity.ok(events);
    }


    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(
            @PathVariable Long id) {

        EventResponseDTO event =
                eventService.getEventById(id);

        return ResponseEntity.ok(event);
    }


    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<EventResponseDTO>> searchEvents(
            @RequestParam String eventName) {

        List<EventResponseDTO> events =
                eventService.searchEvents(eventName);

        return ResponseEntity.ok(events);
    }


    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDTO request) {

        EventResponseDTO updatedEvent =
                eventService.updateEvent(id, request);

        return ResponseEntity.ok(updatedEvent);
    }


    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id) {

        eventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }
}