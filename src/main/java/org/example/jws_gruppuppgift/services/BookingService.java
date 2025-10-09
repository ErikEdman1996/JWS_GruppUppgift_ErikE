package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.entities.TravelBooking;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.exceptions.ResourceNotFoundException;
import org.example.jws_gruppuppgift.exceptions.UnauthorizedActionException;
import org.example.jws_gruppuppgift.repositories.BookingRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookingService implements BookingServiceInterface
{
    private final BookingRepository bookingRepository;
    private final TravelService travelService;
    private final CurrencyService currencyService;

    private static final Logger bookingLogger = LogManager.getLogger("BookingLogger");

    @Autowired
    public BookingService(final BookingRepository bookingRepository, final TravelService travelService,
                          final CurrencyService currencyService)
    {
        this.bookingRepository = bookingRepository;
        this.travelService = travelService;
        this.currencyService = currencyService;
    }

    @Override
    public TravelBooking createBooking(BookingRequestDTO dto, String customer)
    {
        bookingLogger.info("Retrieving travel with ID {}", dto.getTravelId());
        Travel travel = travelService.getTravelById(dto.getTravelId());

        if(travel == null)
        {
            throw new ResourceNotFoundException("Travel", "id", dto.getTravelId());
        }

        TravelBooking newBooking = new TravelBooking(
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

        bookingRepository.save(newBooking);
        bookingLogger.info("Customer: {} booked {} weeks of travel to {}", customer, dto.getWeeks(), travel.getDestination().toString());

        return newBooking;
    }

    @Override
    public TravelBooking cancelBooking(Long id, String customer)
    {
        bookingLogger.info("Retrieving booking with ID {}", id);
        Optional<TravelBooking> booking = bookingRepository.findById(id);

        if(!booking.isPresent())
        {
            bookingLogger.warn("Could not find booking with ID {}", id);
            throw new ResourceNotFoundException("Booking", "id", id);
        }
        else if(!booking.get().getCustomer().equals(customer))
        {
            bookingLogger.warn("Booking with ID {} does not belong to {}", id, customer);
            throw new UnauthorizedActionException("Wrong customer");
        }

        bookingLogger.info("Customer: {} cancelled booking with ID {}", customer, id);
        booking.get().setStatus(TravelBooking.BookingStatus.CANCELLED);

        return bookingRepository.save(booking.get());
    }

    @Override
    public TravelBooking getBooking(Long id)
    {
        bookingLogger.info("Retrieving booking with ID {}", id);
        Optional<TravelBooking> booking = bookingRepository.findById(id);

        if(!booking.isPresent())
        {
            bookingLogger.warn("Could not find booking with ID {}", id);
            throw new ResourceNotFoundException("Booking", "id", id);
        }

        return booking.get();
    }

    @Override
    public TravelBooking updateBooking(TravelBooking booking)
    {
        bookingLogger.info("Retrieving booking with ID {}", booking.getId());
        Optional<TravelBooking> bookingToUpdate = bookingRepository.findById(booking.getId());

        if(!bookingToUpdate.isPresent())
        {
            bookingLogger.warn("Could not find booking with ID {}", booking.getId());
            throw new ResourceNotFoundException("Booking", "id", booking.getId());
        }

        bookingLogger.info("Updating booking with ID {}", booking.getId());
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id)
    {
        bookingLogger.info("Retrieving booking with ID {}", id);
        Optional<TravelBooking> bookingToDelete = bookingRepository.findById(id);

        if(!bookingToDelete.isPresent())
        {
            bookingLogger.warn("Could not find booking with ID {}", id);
            throw new ResourceNotFoundException("Booking", "id", id);
        }

        bookingLogger.info("Deleting booking with ID {}", id);
        bookingRepository.delete(bookingToDelete.get());
    }

    @Override
    public List<TravelBooking> getAllBookings()
    {
        bookingLogger.info("Retrieving all bookings");
        return bookingRepository.findAll();
    }

    @Override
    public List<TravelBooking> getAllActiveAndPastBookings(String customer)
    {
        bookingLogger.info("Retrieving all active and past bookings belonging to {}", customer);
        List<TravelBooking> bookings = bookingRepository.findByCustomerAndStatusIn(customer, List.of(TravelBooking.BookingStatus.ACTIVE, TravelBooking.BookingStatus.PAST));

        return bookings;
    }

    @Override
    public List<TravelBooking> getAllBookingsByStatus(List<TravelBooking.BookingStatus> statuses)
    {
        bookingLogger.info("Retrieving all bookings with the statuses: {}", statuses);
        List<TravelBooking> bookings = bookingRepository.findByStatusIn(statuses);

        return bookings;
    }
}
