package com.example.eventbooking.repository;

import com.example.eventbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository  extends JpaRepository<Booking, Long> {

}
