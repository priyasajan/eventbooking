package com.example.eventbooking.repository;

import com.example.eventbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {


    List<Booking> findByUserId(Long userId);


    List<Booking> findByEventOrganizerId(Long organizerId);

}