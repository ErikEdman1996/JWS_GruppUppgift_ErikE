package org.example.jws_gruppuppgift.entities;

import jakarta.persistence.*;

@Entity
public class Travel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private float pricePerWeek;

    @Column(length = 40, nullable = false)
    private String hotel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @Enumerated(EnumType.STRING)
    @Column
    private AvailabilityStatus status;

    public Travel()
    {

    }

    public Travel(Long id, float pricePerWeek, String hotel, Destination destination)
    {
        this.id = id;
        this.pricePerWeek = pricePerWeek;
        this.hotel = hotel;
        this.destination = destination;
        this.status = AvailabilityStatus.AVAILABLE;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public float getPricePerWeek()
    {
        return pricePerWeek;
    }

    public void setPricePerWeek(float pricePerWeek)
    {
        this.pricePerWeek = pricePerWeek;
    }

    public String getHotel()
    {
        return hotel;
    }

    public void setHotel(String hotel)
    {
        this.hotel = hotel;
    }

    public Destination getDestination()
    {
        return destination;
    }

    public void setDestination(Destination destination)
    {
        this.destination = destination;
    }

    public AvailabilityStatus getStatus() {
        return status;
    }

    public void setStatus(AvailabilityStatus status) {
        this.status = status;
    }

    public enum AvailabilityStatus
    {
        AVAILABLE,
        UNAVAILABLE
    }
}
