package org.example.jws_gruppuppgift.services;

import org.example.jws_gruppuppgift.entities.Travel;

import java.util.List;

public interface TravelServiceInterface
{
    List<Travel> getAllTravels();
    Travel getTravelById(Long id);
}
