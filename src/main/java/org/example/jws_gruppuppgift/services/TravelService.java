package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.repositories.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelService implements TravelServiceInterface
{
    private final TravelRepository travelRepository;

    @Autowired
    public TravelService(final TravelRepository travelRepository)
    {
        this.travelRepository = travelRepository;
    }

    @Override
    public List<Travel> getAllTravels()
    {
        return travelRepository.findAll();
    }

    @Override
    public Travel getTravelById(Long id)
    {
        Optional<Travel> travel = travelRepository.findById(id);

        if(!travel.isPresent())
        {
            return null;
        }

        return travel.get();
    }
}
