package org.example.jws_gruppuppgift.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.exceptions.ResourceNotFoundException;
import org.example.jws_gruppuppgift.repositories.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelService implements TravelServiceInterface
{
    private final TravelRepository travelRepository;
    private static final Logger travelLogger = LogManager.getLogger("TravelLogger");

    @Autowired
    public TravelService(final TravelRepository travelRepository)
    {
        this.travelRepository = travelRepository;
    }

    @Override
    public List<Travel> getAllTravels()
    {
        travelLogger.info("Retrieving all travels");
        return travelRepository.findAll();
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
    public void deleteTravelById(Long id)
    {
        travelLogger.info("Retrieving travel with ID {}", id);
        Optional<Travel> travelToDelete = travelRepository.findById(id);

        if(travelToDelete.isPresent())
        {
            travelLogger.info("Deleting travel with ID {}", id);
            travelRepository.delete(travelToDelete.get());
        }
        else
        {
            travelLogger.warn("Could not find travel with ID {}", id);
            throw new ResourceNotFoundException("Travel", "id", id);
        }
    }
}
