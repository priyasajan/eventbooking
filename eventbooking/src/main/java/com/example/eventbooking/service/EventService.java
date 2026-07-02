package com.example.eventbooking.service;
import com.example.eventbooking.entity.Event;
import com.example.eventbooking.exception.EventNotFoundException;
import com.example.eventbooking.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event){
        return eventRepository.save(event);
    }
    public List<Event> getAllEvents(){
        return eventRepository.findAll();
    }
    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }
    public Event updateEvent(Long id, Event updatedEvent) {

        Event existingEvent = eventRepository.findById(id).orElse(null);

        if (existingEvent != null) {
            existingEvent.setEventName(updatedEvent.getEventName());
            existingEvent.setDescription(updatedEvent.getDescription());
            existingEvent.setVenue(updatedEvent.getVenue());
            existingEvent.setTicketPrice(updatedEvent.getTicketPrice());
            existingEvent.setTotalSeats(updatedEvent.getTotalSeats());

            return eventRepository.save(existingEvent);
        }

        return null;
    }
    public void deleteEvent(Long id){
        Optional<Event> event = eventRepository.findById(id);
        if (event.isPresent()) {
            eventRepository.deleteById(id);
        }else{
            throw new EventNotFoundException("Event not found id:"+id);
        }
    }
}
