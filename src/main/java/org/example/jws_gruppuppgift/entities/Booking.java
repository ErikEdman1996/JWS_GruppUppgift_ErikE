package org.example.jws_gruppuppgift.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false)
    private String customer;

    @Column(name = "departure")
    private LocalDate departureDate;

    @Column(nullable = false)
    private int weeks;

    @Column(length = 40, nullable = false)
    private String hotel;

    @Column(name = "destination_city", length = 40, nullable = false)
    private String city;

    @Column(name = "destination_country", length = 40, nullable = false)
    private String country;

    @Column(name = "total_price_SEK", nullable = false)
    private float totalPriceSEK;

    @Column(name = "total_price_EUR", nullable = false)
    private float totalPriceEUR;

    @Enumerated(EnumType.STRING)
    @Column
    private BookingStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "travel_id", nullable = false)
    @JsonBackReference
    private Travel travel;

    public Booking()
    {

    }

    public Booking(String customer, LocalDate departureDate, int weeks, Travel travel)
    {
        this.customer = customer;
        this.departureDate = departureDate;
        this.weeks = weeks;
        this.travel = travel;

        status = BookingStatus.UPCOMING;

        this.hotel = travel.getHotel();
        this.city = travel.getDestination().getCity();
        this.country = travel.getDestination().getCountry();
        this.totalPriceSEK = travel.getPricePerWeek() * weeks;
        this.totalPriceEUR = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
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

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Travel getTravel() {
        return travel;
    }

    public void setTravel(Travel travel) {
        this.travel = travel;
    }

    public BookingStatus getStatus()
    {
        return status;
    }

    public void setStatus(BookingStatus status)
    {
        this.status = status;
    }

    public float getTotalPriceSEK() {
        return totalPriceSEK;
    }

    public void setTotalPriceSEK(float totalPriceSEK) {
        this.totalPriceSEK = totalPriceSEK;
    }

    public float getTotalPriceEUR() {
        return totalPriceEUR;
    }

    public void setTotalPriceEUR(float totalPriceEUR) {
        this.totalPriceEUR = totalPriceEUR;
    }

    public enum BookingStatus
    {
        ACTIVE,
        UPCOMING,
        PAST,
        CANCELLED
    }
}
