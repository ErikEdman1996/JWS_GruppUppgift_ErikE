package org.example.jws_gruppuppgift.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.jws_gruppuppgift.entities.Booking;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.exceptions.ResourceNotFoundException;
import org.example.jws_gruppuppgift.repositories.BookingRepository;
import org.example.jws_gruppuppgift.repositories.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelService implements TravelServiceInterface
{
    private final TravelRepository travelRepository;
    private final BookingRepository bookingRepository;
    private static final Logger travelLogger = LogManager.getLogger("TravelLogger");

    @Autowired
    public TravelService(final TravelRepository travelRepository, final BookingRepository bookingRepository)
    {
        this.travelRepository = travelRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Travel> getAllTravels(boolean isAdmin)
    {
        if(isAdmin)
        {
            travelLogger.info("Retrieving all travels");
            return travelRepository.findAll();
        }

        travelLogger.info("Retrieving all available travels");
        return travelRepository.findTravelsByStatus(Travel.AvailabilityStatus.AVAILABLE);
    }

    @Override
    public Travel getTravelById(Long id)
    {
        travelLogger.info("Retrieving travel with id {}", id);
        Optional<Travel> travel = travelRepository.findById(id);

        if(!travel.isPresent())
        {
            travelLogger.warn("Could not find travel with id {}", id);
            throw new ResourceNotFoundException("Travel", "id", id);
        }

        return travel.get();
    }

    @Override
    public Travel addTravel(Travel travel)
    {
        travelLogger.info("Adding new travel");
        return travelRepository.save(travel);
    }

    @Override
    public Travel updateTravel(Travel travel)
    {
        travelLogger.info("Retrieving travel with ID {}", travel.getId());
        Optional<Travel> travelToUpdate = travelRepository.findById(travel.getId());

        if(!travelToUpdate.isPresent())
        {
            travelLogger.warn("Could not find travel with id {}", travel.getId());
            throw new ResourceNotFoundException("Travel", "id", travel.getId());
        }

        travelLogger.info("Updating travel with ID {}", travel.getId());
        return travelRepository.save(travel);
    }

    @Override
    public Travel removeTravelById(Long id)
    {
        travelLogger.info("Retrieving travel with ID {}", id);
        Optional<Travel> travelToRemove = travelRepository.findById(id);

        if(!travelToRemove.isPresent())
        {
            travelLogger.warn("Could not find travel with id {}", id);
            throw new ResourceNotFoundException("Travel", "id", id);
        }

        travelLogger.info("Removing travel with ID {}",id);
        travelToRemove.get().setStatus(Travel.AvailabilityStatus.UNAVAILABLE);
        return travelRepository.save(travelToRemove.get());
    }
}
