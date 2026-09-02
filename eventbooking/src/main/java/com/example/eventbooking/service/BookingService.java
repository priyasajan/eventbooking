package com.example.eventbooking.service;

import com.example.eventbooking.dto.BookingRequestDTO;
import com.example.eventbooking.dto.BookingResponseDTO;
import com.example.eventbooking.entity.Booking;
import com.example.eventbooking.entity.BookingStatus;
import com.example.eventbooking.entity.Event;
import com.example.eventbooking.entity.Role;
import com.example.eventbooking.entity.User;
import com.example.eventbooking.exception.EventNotFoundException;
import com.example.eventbooking.repository.BookingRepository;
import com.example.eventbooking.repository.EventRepository;
import com.example.eventbooking.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;


   // create booking

    @Transactional
    public BookingResponseDTO saveBooking(BookingRequestDTO request) {

        // Find User
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        // Find Event
        Event event = eventRepository.findByIdForUpdate(request.getEventId())
                .orElseThrow(() ->
                        new EventNotFoundException("Event not found"));


        // Check available seats
        if (event.getAvailableSeats() == null) {
            throw new RuntimeException(
                    "Available seats are not configured for this event"
            );
        }


        // Check requested tickets
        if (request.getNumberOfTickets() > event.getAvailableSeats()) {

            throw new RuntimeException(
                    "Not enough seats available. Available seats: "
                            + event.getAvailableSeats()
            );
        }


        // Create Booking
        Booking booking = new Booking();

        booking.setUser(user);

        booking.setEvent(event);

        booking.setNumberOfSeats(
                request.getNumberOfTickets()
        );

        booking.setBookingDate(
                LocalDateTime.now()
        );

        booking.setStatus(
                BookingStatus.CONFIRMED
        );


        // Reduce available seats
        event.setAvailableSeats(
                event.getAvailableSeats()
                        - request.getNumberOfTickets()
        );

        eventRepository.save(event);


        // Save Booking
        Booking savedBooking =
                bookingRepository.save(booking);


        return mapToResponseDTO(savedBooking);
    }


    //get booking by id

    public BookingResponseDTO getBookingById(Long id) {

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Booking not found"
                        ));

        return mapToResponseDTO(booking);
    }


  //get booking based on role

    public List<BookingResponseDTO> getAllBookings() {

        // Get currently logged-in user
        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();


        // Find logged-in user
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));


        List<Booking> bookings;


     //admin

        if (loggedInUser.getRole() == Role.ADMIN) {

            // ADMIN can see all bookings
            bookings = bookingRepository.findAll();
        }


       //organizer

        else if (loggedInUser.getRole() == Role.ORGANIZER) {

            // ORGANIZER can see bookings
            // made for their own events
            bookings = bookingRepository.findByEventOrganizerId(
                    loggedInUser.getId()
            );
        }


      //user

        else {

            // USER can see only their own bookings
            bookings = bookingRepository.findByUserId(
                    loggedInUser.getId()
            );
        }


        // Convert Entity -> DTO
        List<BookingResponseDTO> responseList =
                new ArrayList<>();

        for (Booking booking : bookings) {

            responseList.add(
                    mapToResponseDTO(booking)
            );
        }

        return responseList;
    }


  //cancel bookings

    @Transactional
    public BookingResponseDTO cancelBooking(Long id) {

        // Find booking
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Booking not found"
                        ));


        // Get currently logged-in user
        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();


        // Find logged-in user
        User loggedInUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));


        //organizer

        if (loggedInUser.getRole() == Role.ORGANIZER) {

            throw new AccessDeniedException(
                    "Organizer cannot cancel bookings"
            );
        }

//user

        if (loggedInUser.getRole() == Role.USER) {

            // Check booking belongs to logged-in user
            if (!booking.getUser().getId()
                    .equals(loggedInUser.getId())) {

                throw new AccessDeniedException(
                        "You are not allowed to cancel this booking"
                );
            }
        }


    //admin

        // ADMIN can cancel any booking


        // Check already cancelled
        if (booking.getStatus() ==
                BookingStatus.CANCELLED) {

            throw new RuntimeException(
                    "Booking is already cancelled"
            );
        }


        // Get Event
        Event event = booking.getEvent();


        // Restore available seats
        event.setAvailableSeats(
                event.getAvailableSeats()
                        + booking.getNumberOfSeats()
        );

        eventRepository.save(event);


        // Change booking status
        booking.setStatus(
                BookingStatus.CANCELLED
        );


        // Save cancelled booking
        Booking cancelledBooking =
                bookingRepository.save(booking);


        return mapToResponseDTO(
                cancelledBooking
        );
    }



    // ENTITY -> RESPONSE DTO

    private BookingResponseDTO mapToResponseDTO(
            Booking booking) {

        BookingResponseDTO response =
                new BookingResponseDTO();

        response.setId(
                booking.getId()
        );

        response.setUserId(
                booking.getUser().getId()
        );

        response.setEventId(
                booking.getEvent().getId()
        );

        response.setNumberOfSeats(
                booking.getNumberOfSeats()
        );

        response.setBookingDate(
                booking.getBookingDate()
        );

        response.setStatus(
                booking.getStatus()
        );

        return response;
    }
}