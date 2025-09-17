package org.example.jws_gruppuppgift.entities;

import jakarta.persistence.*;

@Entity
public class Travel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String destination;

    public Travel()
    {

    }

    public Travel(Long id, String destination)
    {
        this.id = id;
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
