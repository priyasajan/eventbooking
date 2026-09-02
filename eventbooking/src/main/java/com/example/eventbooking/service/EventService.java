package com.example.eventbooking.service;

import com.example.eventbooking.dto.EventRequestDTO;
import com.example.eventbooking.dto.EventResponseDTO;
import com.example.eventbooking.entity.Event;
import com.example.eventbooking.entity.User;
import com.example.eventbooking.exception.EventAlreadyExistsException;
import com.example.eventbooking.exception.EventNotFoundException;
import com.example.eventbooking.repository.EventRepository;
import com.example.eventbooking.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;



    //  create event
    public EventResponseDTO saveEvent(EventRequestDTO request) {

        if (eventRepository.existsByEventNameAndVenue(
                request.getEventName(),
                request.getVenue())) {

            throw new EventAlreadyExistsException(
                    "Event already exists with the same name and venue");
        }

        // Get currently logged-in user
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Convert DTO to Entity
        Event event = mapToEntity(request);

        // Set logged-in user as organizer
        event.setOrganizer(user);

        Event savedEvent = eventRepository.save(event);

        return mapToResponseDTO(savedEvent);
    }


   //get all events
    public List<EventResponseDTO> getAllEvents(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Event> eventPage =
                eventRepository.findAll(pageable);

        List<Event> events = eventPage.getContent();

        List<EventResponseDTO> responseList =
                new ArrayList<>();

        for (Event event : events) {
            responseList.add(mapToResponseDTO(event));
        }

        return responseList;
    }


    //get event by id
    public EventResponseDTO getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException(
                                "Event not found"));

        return mapToResponseDTO(event);
    }


    //update event
    public EventResponseDTO updateEvent(
            Long id,
            EventRequestDTO request) {

        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException(
                                "Event not found"));

        // Get currently logged-in user
        User loggedInUser = getLoggedInUser();

        /*
         * ADMIN:
         * Can update any event.
         */
        if (loggedInUser.getRole().name().equals("ADMIN")) {

            updateEventDetails(existingEvent, request);

            Event updatedEvent =
                    eventRepository.save(existingEvent);

            return mapToResponseDTO(updatedEvent);
        }

        /*
         * ORGANIZER:
         * Can update only their own event.
         */
        if (!existingEvent.getOrganizer().getId()
                .equals(loggedInUser.getId())) {

            throw new AccessDeniedException(
                    "You are not the owner of this event");
        }

        updateEventDetails(existingEvent, request);

        Event updatedEvent =
                eventRepository.save(existingEvent);

        return mapToResponseDTO(updatedEvent);
    }

//delete
    public void deleteEvent(Long id) {


        Event event = eventRepository.findById(id)
                .orElseThrow(() ->
                        new EventNotFoundException(
                                "Event not found id : " + id));

        // Get currently logged-in user
        User loggedInUser = getLoggedInUser();

        /*
         * ADMIN:
         * Can delete any event.
         */
        if (loggedInUser.getRole().name().equals("ADMIN")) {

            eventRepository.delete(event);
            return;
        }

        /*
         * ORGANIZER:
         * Can delete only their own event.
         */
        if (!event.getOrganizer().getId()
                .equals(loggedInUser.getId())) {

            throw new AccessDeniedException(
                    "You are not the owner of this event");
        }

        eventRepository.delete(event);
    }


    //search events
    public List<EventResponseDTO> searchEvents(
            String eventName) {

        List<Event> events =
                eventRepository
                        .findByEventNameContainingIgnoreCase(
                                eventName);

        List<EventResponseDTO> responseList =
                new ArrayList<>();

        for (Event event : events) {
            responseList.add(mapToResponseDTO(event));
        }

        return responseList;
    }

//get logged in user
    private User getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }


   //update event details
    private void updateEventDetails(
            Event event,
            EventRequestDTO request) {

        event.setEventName(request.getEventName());
        event.setDescription(request.getDescription());
        event.setVenue(request.getVenue());
        event.setTicketPrice(request.getTicketPrice());
        event.setTotalSeats(request.getTotalSeats());
    }

// entity -> response dto
    private EventResponseDTO mapToResponseDTO(
            Event event) {

        EventResponseDTO response =
                new EventResponseDTO();

        response.setId(event.getId());
        response.setEventName(event.getEventName());
        response.setDescription(event.getDescription());
        response.setVenue(event.getVenue());
        response.setTicketPrice(event.getTicketPrice());
        response.setTotalSeats(event.getTotalSeats());

        response.setEventDate(event.getEventDate());
        response.setEventTime(event.getEventTime());

        return response;
    }


    //request dto -> entity
    private Event mapToEntity(
            EventRequestDTO request) {

        Event event = new Event();

        event.setEventName(request.getEventName());
        event.setDescription(request.getDescription());
        event.setVenue(request.getVenue());
        event.setTicketPrice(request.getTicketPrice());
        event.setTotalSeats(request.getTotalSeats());

        event.setEventDate(request.getEventDate());
        event.setEventTime(request.getEventTime());

        // Initially all seats are available
        event.setAvailableSeats(
                request.getTotalSeats()
        );

        return event;
    }
}