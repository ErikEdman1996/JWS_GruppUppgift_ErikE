package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.entities.Booking;

import java.util.List;

public interface BookingServiceInterface
{
    Booking createBooking(BookingRequestDTO dto, String customer);
    Booking cancelBooking(Long id, String customer);
    List<Booking> getAllActiveAndPastBookings(String customer);
}
