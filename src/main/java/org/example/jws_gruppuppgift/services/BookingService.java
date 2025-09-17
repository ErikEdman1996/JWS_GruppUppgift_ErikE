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
    private final CurrencyService currencyService;

    @Autowired
    public BookingService(final BookingRepository bookingRepository, final TravelService travelService,
                          final CurrencyService currencyService)
    {
        this.bookingRepository = bookingRepository;
        this.travelService = travelService;
        this.currencyService = currencyService;
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

        //exchangeratesapi.io tillåter endast endpointen '/latest' för gratisanvändare och basvalutan måste vara EUR.
        //Och då vi går från EUR -> SEK så måste vi dividera SEK med växelkursen för att få antal EUR*/

        float rate = currencyService.getSEKtoEURRate();
        float EUR = newBooking.getTotalPriceSEK() / rate;

        newBooking.setTotalPriceEUR(EUR);

        return bookingRepository.save(newBooking);
    }
}
