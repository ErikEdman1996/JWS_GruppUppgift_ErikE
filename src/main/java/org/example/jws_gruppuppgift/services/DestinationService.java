package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.entities.Destination;
import org.example.jws_gruppuppgift.exceptions.ResourceNotFoundException;
import org.example.jws_gruppuppgift.repositories.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService implements DestinationServiceInterface
{
    private final DestinationRepository destinationRepository;

    @Autowired
    public DestinationService(final DestinationRepository destinationRepository)
    {
        this.destinationRepository = destinationRepository;
    }


    @Override
    public Destination addDestination(Destination destination)
    {
        return destinationRepository.save(destination);
    }

    @Override
    public Destination getDestination(Long id)
    {
        Optional<Destination> destination = destinationRepository.findById(id);

        if(!destination.isPresent())
        {
            throw new ResourceNotFoundException("Destination", "id", id);
        }

        return destination.get();
    }

    @Override
    public Destination updateDestination(Destination destination)
    {
        Optional<Destination> destinationToUpdate = destinationRepository.findById(destination.getId());

        if(!destinationToUpdate.isPresent())
        {
            throw new ResourceNotFoundException("Destination", "id", destination.getId());
        }

        return destinationRepository.save(destination);
    }

    @Override
    public void deleteDestination(Long id)
    {
        Optional<Destination> destinationToDelete = destinationRepository.findById(id);

        if(!destinationToDelete.isPresent())
        {
            throw new ResourceNotFoundException("Destination", "id", id);
        }

        destinationRepository.deleteById(id);
    }

    @Override
    public List<Destination> getAllDestinations()
    {
        return destinationRepository.findAll();
    }
}
