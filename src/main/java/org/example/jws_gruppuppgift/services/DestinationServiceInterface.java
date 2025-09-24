package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.entities.Destination;

import java.util.List;

public interface DestinationServiceInterface
{
    Destination addDestination(Destination destination);
    Destination getDestination(Long id);
    Destination updateDestination(Destination destination);
    void deleteDestination(Long id);
    List<Destination> getAllDestinations();
}
