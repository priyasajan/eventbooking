package com.example.eventbooking.controller;

import com.example.eventbooking.entity.Event;
import com.example.eventbooking.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("api/events")
public class HomeController {
    @Autowired
    private EventService eventService;
    @GetMapping("/hello")
    public String hello(){
        return "Hello Event Booking..";
    }
    @PostMapping
    public Event createEvent(@RequestBody Event event){
        return eventService.saveEvent(event);
    }
    @GetMapping
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }
    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
        return eventService.updateEvent(id, updatedEvent);
    }
    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}

