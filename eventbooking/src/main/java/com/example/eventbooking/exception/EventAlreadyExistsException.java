package com.example.eventbooking.exception;

public class EventAlreadyExistsException extends RuntimeException {

    public EventAlreadyExistsException(String message) {
        super(message);
    }
}