package org.example.jws_gruppuppgift.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.repositories.BookingRepository;
import org.example.jws_gruppuppgift.services.BookingService;
import org.example.jws_gruppuppgift.services.TravelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username  = "johndoe", password = "doe", roles = {"USER"})

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TravelService travelService;

    @Test
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
    }

    @Test
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
                .andExpect(jsonPath("$.customer").value("johndoe"))
                .andExpect(jsonPath("$.hotel").isNotEmpty());
    }
}