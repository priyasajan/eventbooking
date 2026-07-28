package com.example.eventbooking.service;

import com.example.eventbooking.dto.EventRequestDTO;
import com.example.eventbooking.dto.EventResponseDTO;
import com.example.eventbooking.entity.Event;
import com.example.eventbooking.exception.EventAlreadyExistsException;
import com.example.eventbooking.exception.EventNotFoundException;
import com.example.eventbooking.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // CREATE
    public EventResponseDTO saveEvent(EventRequestDTO request) {

        if (eventRepository.existsByEventNameAndVenue(
                request.getEventName(),
                request.getVenue())) {

            throw new EventAlreadyExistsException(
                    "Event already exists with the same name and venue");
        }
        Event event = mapToEntity(request);

        Event savedEvent = eventRepository.save(event);

        return mapToResponseDTO(savedEvent);

    }

    // GET ALL
    public List<EventResponseDTO> getAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventRepository.findAll(pageable);
        List<Event> events = eventPage.getContent();
        List<EventResponseDTO> responseList = new ArrayList<>();

        for (Event event : events) {
            responseList.add(mapToResponseDTO(event));
        }

        return responseList;
    }

    // GET BY ID
    public EventResponseDTO getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        return mapToResponseDTO(event);
    }

    // UPDATE
    public EventResponseDTO updateEvent(Long id, EventRequestDTO request) {

        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        existingEvent.setEventName(request.getEventName());
        existingEvent.setDescription(request.getDescription());
        existingEvent.setVenue(request.getVenue());
        existingEvent.setTicketPrice(request.getTicketPrice());
        existingEvent.setTotalSeats(request.getTotalSeats());

        Event updatedEvent = eventRepository.save(existingEvent);

        return mapToResponseDTO(updatedEvent);
    }

    // DELETE
    public void deleteEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found id : " + id));


        eventRepository.delete(event);
    }

    public List<EventResponseDTO> searchEvents(String eventName) {

        List<Event> events = eventRepository.findByEventNameContainingIgnoreCase(eventName);

        List<EventResponseDTO> responseList = new ArrayList<>();

        for (Event event : events) {
            responseList.add(mapToResponseDTO(event));
        }

        return responseList;
    }

    // Entity -> ResponseDTO
    private EventResponseDTO mapToResponseDTO(Event event) {

        EventResponseDTO response = new EventResponseDTO();

        response.setId(event.getId());
        response.setEventName(event.getEventName());
        response.setDescription(event.getDescription());
        response.setVenue(event.getVenue());
        response.setTicketPrice(event.getTicketPrice());
        response.setTotalSeats(event.getTotalSeats());

        return response;
    }

    // RequestDTO -> Entity
    private Event mapToEntity(EventRequestDTO request) {

        Event event = new Event();

        event.setEventName(request.getEventName());
        event.setDescription(request.getDescription());
        event.setVenue(request.getVenue());
        event.setTicketPrice(request.getTicketPrice());
        event.setTotalSeats(request.getTotalSeats());

        return event;


    }
}