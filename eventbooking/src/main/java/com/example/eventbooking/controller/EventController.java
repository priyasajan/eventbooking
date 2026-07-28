package com.example.eventbooking.controller;

import com.example.eventbooking.dto.EventRequestDTO;
import com.example.eventbooking.dto.EventResponseDTO;
import com.example.eventbooking.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("api/events")
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public EventResponseDTO saveEvent(
            @Valid @RequestBody EventRequestDTO request) {

        return eventService.saveEvent(request);
    }
    @GetMapping
    public List<EventResponseDTO> getAllEvents(
            @RequestParam int page,
            @RequestParam int size
    ){
        return eventService.getAllEvents(page, size);
    }
    @GetMapping("/{id}")
    public EventResponseDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }
    @GetMapping("/search")
    public List<EventResponseDTO> searchEvents(
            @RequestParam String eventName) {

        return eventService.searchEvents(eventName);
    }
    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDTO request

    ) {

        return eventService.updateEvent(id, request);
    }
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);


    }

}

