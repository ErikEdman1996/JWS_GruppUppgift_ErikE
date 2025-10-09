package org.example.jws_gruppuppgift.controllers;

import org.example.jws_gruppuppgift.entities.TravelBooking;
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
    public ResponseEntity<List<TravelBooking>> getAllCancelledBookings()
    {
        List<TravelBooking> bookings = bookingService.getAllBookingsByStatus(List.of(TravelBooking.BookingStatus.CANCELLED));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/listupcoming")
    public ResponseEntity<List<TravelBooking>> getAllUpcomingBookings()
    {
        List<TravelBooking> bookings = bookingService.getAllBookingsByStatus(List.of(TravelBooking.BookingStatus.UPCOMING));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/listpast")
    public ResponseEntity<List<TravelBooking>> getAllPastBookings()
    {
        List<TravelBooking> bookings = bookingService.getAllBookingsByStatus(List.of(TravelBooking.BookingStatus.PAST));

        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<TravelBooking> getBooking(@PathVariable Long id)
    {
        TravelBooking booking = bookingService.getBooking(id);

        return ResponseEntity.ok(booking);
    }

    @PutMapping("/updatebooking")
    public ResponseEntity<TravelBooking> updateBooking(@RequestBody TravelBooking booking)
    {
        TravelBooking bookingToUpdate = bookingService.getBooking(booking.getId());

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

    @PutMapping("/removetravel/{id}")
    public ResponseEntity<Travel> deleteTravel(@PathVariable Long id)
    {
        Travel travel = travelService.removeTravelById(id);

        return ResponseEntity.ok(travel);
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
