package org.example.jws_gruppuppgift.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.entities.Booking;
import org.example.jws_gruppuppgift.entities.Destination;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.repositories.BookingRepository;
import org.example.jws_gruppuppgift.services.BookingService;
import org.example.jws_gruppuppgift.services.TravelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TravelService travelService;

    @BeforeEach
    void setup()
    {
        Destination destination = new Destination(1L, "Stockholm", "Sweden");
        Travel travel = new Travel(1L, 1500f, "The Waldorf Hilton", destination, null);

        Booking booking = new Booking("Erik", LocalDate.of(2025, 10, 10), 2, travel);

        // Stub repository behavior
        Mockito.when(bookingRepository.findAll()).thenReturn(List.of(booking));
        Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(booking);

        // If travelService is used, mock it too
        Mockito.when(travelService.getAllTravels(false))
                .thenReturn(List.of(travel));
    }

    @Test
    @WithMockUser(username = "Erik", password = "Edman", roles = {"USER"})
    void getAllTravels_ShouldReturnAllTravels() throws Exception
    {
        //Act & Assert
        mockMvc.perform(get("/api/wigelltravels/v1/travels")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].hotel").value("The Waldorf Hilton"))
                .andExpect(jsonPath("$[1].hotel").value("The Plaza"))
                .andExpect(jsonPath("$[2].hotel").value("Dai-ichi Hotel"))
                .andExpect(jsonPath("$[1].destination.city").value("New York"))
                .andExpect(jsonPath("$[1].pricePerWeek").value(1200));

        verify(travelService.getAllTravels(false));
    }

    @Test
    @WithMockUser(username = "Erik", password = "Edman", roles = {"USER"})
    void createBooking_ShouldReturnCreatedBooking() throws Exception
    {
        //Arrange
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setTravelId(2L);
        dto.setDepartureDate(LocalDate.of(2025, 10, 10));
        dto.setWeeks(2);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String dtoJson = objectMapper.writeValueAsString(dto);

        //Act & Assert
        mockMvc.perform(post("/api/wigelltravels/v1/booktrip")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customer").value("Erik"))
                .andExpect(jsonPath("$.hotel").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "Erik", roles = {"USER"})
    void createBooking_ShouldReturn404() throws Exception {
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setTravelId(999L);
        dto.setDepartureDate(LocalDate.of(2025, 10, 10));
        dto.setWeeks(2);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String dtoJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/api/wigelltravels/v1/booktrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Erik", password = "Edman", roles = {"USER"})
    void cancelBooking_ShouldUpdateCorrectBooking() throws Exception
    {
        // Act & Assert
        mockMvc.perform(put("/api/wigelltravels/v1/canceltrip/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("CANCELLED"));
    }

    @Test
    @WithMockUser(username = "Erik", password = "Edman", roles = {"USER"})
    void cancelBooking_ShouldReturn404() throws Exception
    {
        mockMvc.perform(put("/api/wigelltravels/v1/canceltrip/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "Erik", password = "Edman", roles = {"USER"})
    void getActiveAndPastBookings_ShouldReturnBookingList() throws Exception
    {
        mockMvc.perform(get("/api/wigelltravels/v1/mybookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].customer").value("Erik"));
    }
}