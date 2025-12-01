package com.hamroevent.Venue_Service.service;


import com.hamroevent.Venue_Service.entity.Venue;
import com.hamroevent.Venue_Service.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueService {

    @Autowired
    private VenueRepository venueRepository;

    // Get all venues
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    // Get single venue by ID
    public Venue getVenueById(Long id) {
        return venueRepository.findById(id).orElse(null);
    }

    // Create new venue
    public Venue createVenue(Venue venue) {
        return venueRepository.save(venue);
    }

    // Update venue
    public Venue updateVenue(Long id, Venue venueDetails) {
        Venue existingVenue = getVenueById(id);
        if (existingVenue == null) {
            throw new RuntimeException("Venue not found");
        }

        existingVenue.setName(venueDetails.getName());
        existingVenue.setAddress(venueDetails.getAddress());
        existingVenue.setCapacity(venueDetails.getCapacity());

        return venueRepository.save(existingVenue);
    }

    // Delete venue
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
}


