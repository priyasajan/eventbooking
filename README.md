# Smart Event Booking System - Backend

A secure REST API for managing events, users, authentication, and event ticket bookings.

This project is developed using Java and Spring Boot. It allows users to explore events, authenticate using JWT, book tickets, and manage their bookings. The system also supports role-based access for Users, Organizers, and Admins.

## Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Spring Security
- JWT Authentication
- BCrypt Password Encryption
- MySQL
- Maven
- Lombok
- Swagger / OpenAPI
- Postman

# Features

## Authentication & Security

- User registration and login
- JWT-based authentication
- Passwords encrypted using BCrypt
- Protected API endpoints
- Role-based authorization
- Secure access using Bearer Token

### Roles

- USER
- ORGANIZER
- ADMIN

## Event Management

- Create events
- View all events
- View event details by ID
- Search events by event name
- Update events
- Delete events
- Pagination support

## User Management

- Create users
- View all users
- View user by ID
- Update user details
- Delete users
- Email validation
- Duplicate email prevention
- Password encryption
- Role management

## Booking Management

- Book tickets for an event
- Select number of tickets
- Check available seats before booking
- Automatically reduce available seats after booking
- View booking details
- View bookings based on user role
- Cancel bookings
- Automatically restore seats after cancellation
- Prevent booking when insufficient seats are available

## Role-Based Booking Access

### Admin

- Can view all bookings
- Can cancel bookings

### Organizer

- Can view bookings for their own events
- Cannot cancel bookings

### User

- Can create bookings
- Can view their own bookings
- Can cancel their own bookings only

##  Database

This project uses MySQL.

Database Name:
event_booking_db
