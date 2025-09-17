package org.example.jws_gruppuppgift.entities;

import jakarta.persistence.*;

@Entity
public class Destination
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String city;

    @Column
    private String country;

    public Destination()
    {

    }

    public Destination(Long id, String city, String country)
    {
        this.id = id;
        this.city = city;
        this.country = country;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
}
