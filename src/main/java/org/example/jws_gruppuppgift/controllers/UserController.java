package org.example.jws_gruppuppgift.controllers;

import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wigelltravels/v1")
public class UserController
{
    private final TravelService travelService;

    @Autowired
    public UserController(final TravelService travelService)
    {
        this.travelService = travelService;
    }

    @GetMapping("/travels")
    public ResponseEntity<List<Travel>> getTravels()
    {
        List<Travel> travels = travelService.getAllTravels();

        return ResponseEntity.ok(travels);
    }
}
