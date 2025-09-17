package org.example.jws_gruppuppgift.entities;

import jakarta.persistence.*;

@Entity
public class Travel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private float pricePerWeek;

    @Column
    private String hotel;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id")
    private Destination destination;

    public Travel()
    {

    }

    public Travel(Long id, float pricePerWeek, String hotel, Destination destination)
    {
        this.id = id;
        this.pricePerWeek = pricePerWeek;
        this.hotel = hotel;
        this.destination = destination;
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
}
