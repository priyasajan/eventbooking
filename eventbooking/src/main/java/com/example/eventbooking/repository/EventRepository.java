package com.example.eventbooking.repository;

import java.util.List;
import java.util.Optional;

import com.example.eventbooking.entity.Event;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EventRepository extends JpaRepository<Event, Long> {

    boolean existsByEventNameAndVenue(
            String eventName,
            String venue
    );

    List<Event> findByEventNameContainingIgnoreCase(
            String eventName
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Event e WHERE e.id = :id")
    Optional<Event> findByIdForUpdate(
            @Param("id") Long id
    );
}