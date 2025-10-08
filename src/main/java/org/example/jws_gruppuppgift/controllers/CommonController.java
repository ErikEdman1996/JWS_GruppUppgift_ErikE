package org.example.jws_gruppuppgift.controllers;

import org.example.jws_gruppuppgift.entities.Travel;
import org.example.jws_gruppuppgift.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/wigelltravels/v1")
public class CommonController
{
    final TravelService travelService;

    @Autowired
    public CommonController(final TravelService travelService)
    {
        this.travelService = travelService;
    }

    @GetMapping("/travels")
    public ResponseEntity<List<Travel>> getTravels(Authentication auth)
    {
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        List<Travel> travels = travelService.getAllTravels(isAdmin);

        return ResponseEntity.ok(travels);
    }
}
