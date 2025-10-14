package org.example.jws_gruppuppgift.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.jws_gruppuppgift.dtos.BookingRequestDTO;
import org.example.jws_gruppuppgift.entities.TravelBooking;
import org.example.jws_gruppuppgift.entities.Destination;
import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.repositories.BookingRepository;
import org.example.jws_gruppuppgift.repositories.DestinationRepository;
import org.example.jws_gruppuppgift.repositories.TravelRepository;
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
import org.springframework.test.context.ActiveProfiles;
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

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TravelService travelService;

    @BeforeEach
    void setup() {
        bookingRepository.deleteAll();
        travelRepository.deleteAll();
        destinationRepository.deleteAll();

        Destination rome = new Destination(null, "Rome", "Italy");
        Destination london = new Destination(null, "London", "England");
        Destination berlin = new Destination(null, "Berlin", "Germany");
        Destination newYork = new Destination(null, "New York", "United States");
        Destination tokyo = new Destination(null, "Tokyo", "Japan");

        destinationRepository.saveAll(List.of(rome, london, berlin, newYork, tokyo));

        Travel travel1 = new Travel(null, 1000, "The Waldorf Hilton", london, null);
        travel1.setStatus(Travel.AvailabilityStatus.AVAILABLE);

        Travel travel2 = new Travel(null, 1200, "The Plaza", newYork, null);
        travel2.setStatus(Travel.AvailabilityStatus.AVAILABLE);

        Travel travel3 = new Travel(null, 1500, "Dai-ichi Hotel", tokyo, null);
        travel3.setStatus(Travel.AvailabilityStatus.AVAILABLE);

        travelRepository.saveAll(List.of(travel1, travel2, travel3));

        TravelBooking booking1 = new TravelBooking("Erik", LocalDate.of(2025, 10, 5), 2, travel2);
        booking1.setStatus(TravelBooking.BookingStatus.UPCOMING);
        booking1.setTotalPriceEUR(218);
        booking1.setTotalPriceSEK(2400);

        TravelBooking booking2 = new TravelBooking("Erik", LocalDate.of(2025, 8, 5), 2, travel2);
        booking2.setStatus(TravelBooking.BookingStatus.PAST);
        booking2.setTotalPriceEUR(218);
        booking2.setTotalPriceSEK(2400);

        TravelBooking booking3 = new TravelBooking("Erik", LocalDate.of(2025, 9, 10), 2, travel2);
        booking3.setStatus(TravelBooking.BookingStatus.ACTIVE);
        booking3.setTotalPriceEUR(218);
        booking3.setTotalPriceSEK(2400);

        bookingRepository.saveAll(List.of(booking1, booking2, booking3));

        travel2.setBookings(List.of(booking1, booking2, booking3));
        travelRepository.save(travel2);
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
    }

    @Test
    @WithMockUser(username = "Erik", password = "Edman", roles = {"USER"})
    void createBooking_ShouldReturnCreatedBooking() throws Exception {
        // Arrange: dynamically get a travel for booking
        Travel travel = travelRepository.findAll().stream()
                .filter(t -> t.getHotel().equals("The Plaza"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Test setup failed: travel not found"));

        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setTravelId(travel.getId()); // use actual ID
        dto.setDepartureDate(LocalDate.of(2025, 10, 10));
        dto.setWeeks(2);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String dtoJson = objectMapper.writeValueAsString(dto);

        // Act & Assert
        mockMvc.perform(post("/api/wigelltravels/v1/booktrip")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customer").value("Erik"))
                .andExpect(jsonPath("$.hotel").value(travel.getHotel())); // assert correct hotel dynamically
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
    void cancelBooking_ShouldUpdateCorrectBooking() throws Exception {
        TravelBooking booking = bookingRepository.findAll().get(0);
        mockMvc.perform(put("/api/wigelltravels/v1/canceltrip/{id}", booking.getId()))
                .andExpect(status().isOk())
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