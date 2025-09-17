package org.example.jws_gruppuppgift.dtos;

import java.time.LocalDate;

public class BookingRequestDTO
{
    private Long travelId;
    private LocalDate departureDate;
    private int weeks;

    public BookingRequestDTO()
    {

    }

    public BookingRequestDTO(Long travelId, LocalDate departureDate, int weeks)
    {
        this.travelId = travelId;
        this.departureDate = departureDate;
        this.weeks = weeks;
    }

    public Long getTravelId() {
        return travelId;
    }

    public void setTravelId(Long travelId) {
        this.travelId = travelId;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }
}
