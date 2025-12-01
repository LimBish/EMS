package com.hamroevent.Venue_Service.controller;


import com.hamroevent.Venue_Service.entity.Venue;
import com.hamroevent.Venue_Service.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    // Get all venues
    @GetMapping({"", "/"})
    public List<Venue> getAllVenues() {
        return venueService.getAllVenues();
    }

    // Get venue by ID
    @GetMapping("/{id}")
    public Venue getVenueById(@PathVariable Long id) {
        return venueService.getVenueById(id);
    }

    // Create a new venue
    @PostMapping
    public Venue createVenue(@RequestBody Venue venue) {
        return venueService.createVenue(venue);
    }

    // Update venue
    @PutMapping("/{id}")
    public Venue updateVenue(@PathVariable Long id, @RequestBody Venue venue) {
        return venueService.updateVenue(id, venue);
    }

    // Delete venue
    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
    }
}

