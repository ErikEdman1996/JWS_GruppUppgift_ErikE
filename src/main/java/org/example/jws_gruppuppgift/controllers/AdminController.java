package org.example.jws_gruppuppgift.controllers;

import org.apache.coyote.Response;
import org.example.jws_gruppuppgift.entities.Booking;
import org.example.jws_gruppuppgift.entities.Destination;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.services.BookingService;
import org.example.jws_gruppuppgift.services.DestinationService;
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
    final DestinationService destinationService;

    @Autowired
    public AdminController(final BookingService bookingService, final TravelService travelService, final DestinationService destinationService)
    {
        this.bookingService = bookingService;
        this.travelService = travelService;
        this.destinationService = destinationService;
    }


    /********** BOOKING ENDPOINTS ************/
    @GetMapping("/listcanceled")
    public ResponseEntity<List<Booking>> getAllCancelledBookings()
    {
        List<Booking> bookings = bookingService.getAllBookingsByStatus(List.of(Booking.BookingStatus.CANCELLED));

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

    @GetMapping("/booking/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long id)
    {
        Booking booking = bookingService.getBooking(id);

        return ResponseEntity.ok(booking);
    }

    @PutMapping("/updatebooking")
    public ResponseEntity<Booking> updateBooking(@RequestBody Booking booking)
    {
        Booking bookingToUpdate = bookingService.getBooking(booking.getId());

        return ResponseEntity.ok(bookingService.updateBooking(bookingToUpdate));
    }

    @DeleteMapping("/deletebooking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long id)
    {
        bookingService.deleteBooking(id);

        return ResponseEntity.noContent().build();
    }

    /********** TRAVEL ENDPOINTS ************/
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

    /********** DESTINATION ENDPOINTS ************/
    @GetMapping("/destination/{id}")
    public ResponseEntity<Destination> getDestination(@PathVariable Long id)
    {
        Destination destination = destinationService.getDestination(id);

        return ResponseEntity.ok(destination);
    }

    @PostMapping("/add_destination")
    public ResponseEntity<Destination> addDestination(@RequestBody Destination destination)
    {
        Destination destinationToAdd = destinationService.addDestination(destination);

        return ResponseEntity.status(HttpStatus.CREATED).body(destinationToAdd);
    }

    @PutMapping("/updatedestination")
    public ResponseEntity<Destination> updateDestination(@RequestBody Destination destination)
    {
        Destination destinationToUpdate = destinationService.updateDestination(destination);

        return ResponseEntity.ok(destinationToUpdate);
    }

    @DeleteMapping("/deletedestination/{id}")
    public ResponseEntity<?> deleteDestination(@PathVariable Long id)
    {
        destinationService.deleteDestination(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listdestinations")
    public ResponseEntity<List<Destination>> getAllDestinations()
    {
        List<Destination> destinations = destinationService.getAllDestinations();

        return ResponseEntity.ok(destinations);
    }
}
