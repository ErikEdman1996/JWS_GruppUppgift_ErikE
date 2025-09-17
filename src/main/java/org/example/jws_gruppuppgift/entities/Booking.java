package org.example.jws_gruppuppgift.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Booking
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String customer;

    @Column(name = "departure")
    private LocalDate departureDate;

    @Column
    private int weeks;

    @Column
    private String hotel;

    @Column(name = "destination_city")
    private String city;

    @Column(name = "destination_country")
    private String country;

    @Column
    private float totalPrice;

    @Enumerated(EnumType.STRING)
    @Column
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "travel_id")
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
        this.totalPrice = travel.getPricePerWeek() * weeks;
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

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
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

    public enum BookingStatus
    {
        ACTIVE,
        UPCOMING,
        PAST,
        CANCELED
    }
}
