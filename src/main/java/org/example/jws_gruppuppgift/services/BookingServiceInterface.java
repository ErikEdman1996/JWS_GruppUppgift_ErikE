package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.entities.TravelBooking;

import java.util.List;

public interface BookingServiceInterface
{
    TravelBooking createBooking(BookingRequestDTO dto, String customer);
    TravelBooking cancelBooking(Long id, String customer);
    TravelBooking getBooking(Long id);
    TravelBooking updateBooking(TravelBooking booking);
    void deleteBooking(Long id);
    List<TravelBooking> getAllBookings();
    List<TravelBooking> getAllActiveAndPastBookings(String customer);
    List<TravelBooking> getAllBookingsByStatus(List<TravelBooking.BookingStatus> statuses);
}
