package org.example.jws_gruppuppgift.services;
import org.example.jws_gruppuppgift.entities.Destination;
import org.example.jws_gruppuppgift.exceptions.ResourceNotFoundException;
import org.example.jws_gruppuppgift.repositories.TravelRepository;
import org.mockito.InjectMocks;

import org.example.jws_gruppuppgift.entities.Travel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelServiceTest
{
    @Mock
    private TravelRepository travelRepository;

    @InjectMocks
    private TravelService travelService;

    private Travel travel;
    private Destination destination;

    @BeforeEach
    void setup()
    {
        destination = new Destination(1L, "Paris", "France");
        travel = new Travel(1L, 1200.0f, "Hotel Lux", destination);
        travel.setStatus(Travel.AvailabilityStatus.AVAILABLE);
    }

    @Test
    void getAllTravels_ShouldReturnAllTravels()
    {
        //Arrange
        when(travelRepository.findAll()).thenReturn(List.of(travel));

        //Act & Assert
        List<Travel> travels = travelService.getAllTravels();

        assertEquals(1, travels.size());
        assertEquals("Hotel Lux", travels.get(0).getHotel());
        assertEquals("Paris", travels.get(0).getDestination().getCity());

        verify(travelRepository).findAll();
    }

    @Test
    void getTravelById_ShouldReturnCorrectTravel()
    {
        //Arrange
        when(travelRepository.findById(1L)).thenReturn(Optional.ofNullable(travel));

        //Act & Assert
        Travel testTravel = travelService.getTravelById(1L);

        assertEquals(1L, testTravel.getId());
        assertEquals("Hotel Lux", testTravel.getHotel());
        assertEquals("Paris", testTravel.getDestination().getCity());
        assertEquals(1200.0f, travel.getPricePerWeek());
        assertEquals(Travel.AvailabilityStatus.AVAILABLE, testTravel.getStatus());

        verify(travelRepository).findById(1L);
    }

    @Test
    void getTravelById_ShouldThrowResourceNotFoundException()
    {
        //Arrange
        when(travelRepository.findById(2L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> travelService.getTravelById(2L));

        verify(travelRepository).findById(2L);

    }

    @Test
    void addTravel_ShouldSaveAndReturnCorrectTravel()
    {
        //Arrange
        when(travelRepository.save(travel)).thenReturn(travel);

        //Act & Assert
        Travel testTravel = travelService.addTravel(travel);

        assertEquals(travel, testTravel);

        verify(travelRepository).save(travel);
    }

    @Test
    void updateTravel_ShouldUpdateAndReturnCorrectTravel()
    {
        //Arrange
        when(travelRepository.findById(1L)).thenReturn(Optional.ofNullable(travel));
        when(travelRepository.save(travel)).thenReturn(travel);

        //Act & Assert
        Travel testTravel = travelService.updateTravel(travel);
        assertEquals(travel, testTravel);

        verify(travelRepository).findById(1L);
        verify(travelRepository).save(travel);
    }

    @Test
    void updateTravel_ShouldThrowResourceNotFoundException()
    {
        travel.setId(2L);

        //Arrange
        when(travelRepository.findById(2L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> travelService.updateTravel(travel));

        verify(travelRepository).findById(2L);
    }

    @Test
    void deleteTravelById_ShouldDeleteCorrectTravel()
    {
        //Arrange
        when(travelRepository.findById(1L)).thenReturn(Optional.ofNullable(travel));

        //Act & Assert
        travelService.deleteTravelById(1L);

        verify(travelRepository).delete(travel);
    }

    @Test
    void deleteTravelById_ShouldThrowResourceNotFoundException()
    {
        //Arrange
        when(travelRepository.findById(2L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> travelService.deleteTravelById(2L));

        verify(travelRepository).findById(2L);
    }
}