package org.example.jws_gruppuppgift.controllers;

import org.example.jws_gruppuppgift.entities.Booking;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.services.BookingService;
import org.example.jws_gruppuppgift.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Booking>> getAllCanceledBookings()
    {
        List<Booking> bookings = bookingService.getAllBookingsByStatus(List.of(Booking.BookingStatus.CANCELED));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<Booking>> getAllUpcomingBookings()
    {
        List<Booking> bookings = bookingService.getAllBookingsByStatus(List.of(Booking.BookingStatus.UPCOMING));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<Booking>> getAllPastBookings()
    {
        List<Booking> bookings = bookingService.getAllBookingsByStatus(List.of(Booking.BookingStatus.PAST));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/travels")
    public ResponseEntity<List<Travel>> getAllTravels()
    {
        List<Travel> travels = travelService.getAllTravels();

        return ResponseEntity.ok(travels);
    }

    @PostMapping("/addtravel")
    public ResponseEntity<Travel> addTravel(@RequestBody Travel travel)
    {
        Travel addedTravel = travelService.addTravel(travel);

        return ResponseEntity.status(HttpStatus.CREATED).body(addedTravel);
    }

    @PutMapping("/updatetravel")
    public ResponseEntity<Travel> updateTravel(@RequestBody Travel travel)
    {
        Travel updatedTravel = travelService.updateTravel(travel);

        return ResponseEntity.ok(updatedTravel);
    }

    @DeleteMapping("/removetravel/{id}")
    public ResponseEntity<?> deleteTravel(@PathVariable Long id)
    {
        travelService.deleteTravelById(id);

        return ResponseEntity.noContent().build();
    }
}
