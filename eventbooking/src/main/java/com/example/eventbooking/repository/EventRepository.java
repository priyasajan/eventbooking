package com.example.eventbooking.repository;
import java.util.List;

import com.example.eventbooking.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByEventNameAndVenue(String eventName, String venue);
    List<Event> findByEventNameContainingIgnoreCase(String eventName);

}