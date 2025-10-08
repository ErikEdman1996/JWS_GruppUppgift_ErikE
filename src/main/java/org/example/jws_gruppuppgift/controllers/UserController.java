package org.example.jws_gruppuppgift.controllers;

import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.entities.Booking;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.services.BookingService;
import org.example.jws_gruppuppgift.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/wigelltravels/v1")
public class UserController
{
    private final TravelService travelService;
    private final BookingService bookingService;

    @Autowired
    public UserController(final TravelService travelService, final BookingService bookingService)
    {
        this.travelService = travelService;
        this.bookingService = bookingService;
    }

    @PostMapping("/booktrip")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO, Principal principal)
    {
        Booking createdBooking = bookingService.createBooking(bookingRequestDTO, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
    }

    @PutMapping("/canceltrip/{id}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id, Principal principal)
    {
        Booking cancelledBooking = bookingService.cancelBooking(id, principal.getName());

        return ResponseEntity.ok(cancelledBooking);
    }

    @GetMapping("/mybookings")
    public ResponseEntity<List<Booking>> getActiveAndPastBookings(Principal principal)
    {
        List<Booking> bookings = bookingService.getAllActiveAndPastBookings(principal.getName());

        return ResponseEntity.ok(bookings);
    }


}
