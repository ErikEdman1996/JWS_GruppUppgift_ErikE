package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.entities.Booking;

public interface BookingServiceInterface
{
    Booking createBooking(BookingRequestDTO dto, String customer);
}
