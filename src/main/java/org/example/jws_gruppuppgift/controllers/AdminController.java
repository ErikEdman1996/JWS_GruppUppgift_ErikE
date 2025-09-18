package org.example.jws_gruppuppgift.controllers;

import org.example.jws_gruppuppgift.entities.Booking;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.services.BookingService;
import org.example.jws_gruppuppgift.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wigelltravels/v1")
public class AdminController
{
    final BookingService bookingService;
    final TravelService travelService;

    @Autowired
    public AdminController(final BookingService bookingService, final TravelService travelService)
    {
        this.bookingService = bookingService;
        this.travelService = travelService;
    }

    @GetMapping("/listcanceled")
    ResponseEntity<List<Booking>> getAllCanceledBookings()
    {
        List<Booking> bookings = bookingService.getAllBookingsByStatus(List.of(Booking.BookingStatus.CANCELED));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/listupcoming")
    ResponseEntity<List<Booking>> getAllUpcomingBookings()
    {
        List<Booking> bookings = bookingService.getAllBookingsByStatus(List.of(Booking.BookingStatus.UPCOMING));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/listpast")
    ResponseEntity<List<Booking>> getAllPastBookings()
    {
        List<Booking> bookings = bookingService.getAllBookingsByStatus(List.of(Booking.BookingStatus.PAST));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/travels")
    ResponseEntity<List<Travel>> getAllTravels()
    {
        List<Travel> travels = travelService.getAllTravels();

        return ResponseEntity.ok(travels);
    }
}
