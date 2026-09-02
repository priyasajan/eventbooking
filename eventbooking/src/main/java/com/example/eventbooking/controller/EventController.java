
        package com.example.eventbooking.controller;

import com.example.eventbooking.dto.EventRequestDTO;
import com.example.eventbooking.dto.EventResponseDTO;
import com.example.eventbooking.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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


   //create event oraganizer nu admin mathrame access ullu

    @Operation(
            summary = "Create a new event",
            description = "Organizer and Admin can create new events.",
            tags = {"Organizer", "Admin"}
    )
    @PostMapping
    public ResponseEntity<EventResponseDTO> saveEvent(
            @Valid @RequestBody EventRequestDTO request) {

        EventResponseDTO response =
                eventService.saveEvent(request);

        return ResponseEntity.ok(response);
    }


    // get all events user ,admin, organizer access und

    @Operation(
            summary = "Get all events",
            description = "View all available events.",
            tags = {"User", "Organizer", "Admin"}
    )
    @GetMapping
    public ResponseEntity<List<EventResponseDTO>> getAllEvents(
            @RequestParam int page,
            @RequestParam int size) {

        List<EventResponseDTO> events =
                eventService.getAllEvents(page, size);

        return ResponseEntity.ok(events);
    }


   //get event by id

    @Operation(
            summary = "Get event by ID",
            description = "View details of a specific event.",
            tags = {"User", "Organizer", "Admin"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDTO> getEventById(
            @PathVariable Long id) {

        EventResponseDTO event =
                eventService.getEventById(id);

        return ResponseEntity.ok(event);
    }


   //search events

    @Operation(
            summary = "Search events by name",
            description = "Search available events using event name.",
            tags = {"User", "Organizer", "Admin"}
    )
    @GetMapping("/search")
    public ResponseEntity<List<EventResponseDTO>> searchEvents(
            @RequestParam String eventName) {

        List<EventResponseDTO> events =
                eventService.searchEvents(eventName);

        return ResponseEntity.ok(events);
    }

//update events

    @Operation(
            summary = "Update an event",
            description = "Organizer and Admin can update events.",
            tags = {"Organizer", "Admin"}
    )
    @PutMapping("/{id}")
    public ResponseEntity<EventResponseDTO> updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDTO request) {

        EventResponseDTO updatedEvent =
                eventService.updateEvent(id, request);

        return ResponseEntity.ok(updatedEvent);
    }


 //delete

    @Operation(
            summary = "Delete an event",
            description = "Organizer and Admin can delete events.",
            tags = {"Organizer", "Admin"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(
            @PathVariable Long id) {

        eventService.deleteEvent(id);

        return ResponseEntity.noContent().build();
    }
}
