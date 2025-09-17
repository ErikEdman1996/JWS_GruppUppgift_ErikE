package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.entities.Booking;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService implements BookingServiceInterface
{
    private final BookingRepository bookingRepository;
    private final TravelService travelService;

    @Autowired
    public BookingService(final BookingRepository bookingRepository, final TravelService travelService)
    {
        this.bookingRepository = bookingRepository;
        this.travelService = travelService;
    }

    @Override
    public Booking createBooking(BookingRequestDTO dto, String customer)
    {
        Travel travel = travelService.getTravelById(dto.getTravelId());

        Booking newBooking = new Booking(
                customer,
                dto.getDepartureDate(),
                dto.getWeeks(),
                travel
        );

        return bookingRepository.save(newBooking);
    }
}
