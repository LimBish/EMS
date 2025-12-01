
package com.hamroevent.event_service.service;

import com.hamroevent.event_service.dto.EventDetailsDTO;
import com.hamroevent.event_service.dto.EventRequestDTO;
import com.hamroevent.event_service.dto.UserDTO;
import com.hamroevent.event_service.dto.VenueDTO;
import com.hamroevent.event_service.entity.Event;
import com.hamroevent.event_service.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EventRepository eventRepository;

    @Value("${venue.service.uri}")
    private String venueServiceUri;

    @Value("${user.service.uri}")
    private String userServiceUri;

    // GET ALL EVENTS
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // GET EVENT BY ID
    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));
    }

    // GET EVENT DETAILS (with Venue and Organizer)
    public EventDetailsDTO getEventDetails(String id) {
        Event event = getEventById(id);
        VenueDTO venue = fetchVenue(event.getVenueId());
        UserDTO organizer = fetchOrganizer(event.getOrganizerId());

        EventDetailsDTO details = new EventDetailsDTO();
        details.setId(event.getId());
        details.setTitle(event.getTitle());
        details.setDescription(event.getDescription());
        details.setEventDate(event.getEventDate());
        details.setStatus(event.getStatus());
        details.setVenue(venue);
        details.setOrganizer(organizer);

        return details;
    }

    // CREATE EVENT
    public Event createEvent(EventRequestDTO dto) {
        // Validate external references
        if (dto.getVenueId() == null) {
            throw new IllegalArgumentException("Venue ID is required");
        }
        if (dto.getOrganizerId() == null) {
            throw new IllegalArgumentException("Organizer ID is required");
        }
        VenueDTO venue = fetchVenue(dto.getVenueId());
        UserDTO organizer = fetchOrganizer(dto.getOrganizerId());

        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setVenueId(dto.getVenueId());
        event.setOrganizerId(dto.getOrganizerId());
        event.setEventTime(dto.getEventTime());
        event.setStatus(dto.getStatus());
        event.setMaxAttendees(dto.getMaxAttendees());

        return eventRepository.save(event);
    }

    // UPDATE EVENT
    public Event updateEvent(String id, EventRequestDTO dto) {
        Event existing = getEventById(id);

        // Revalidate venue/organizer only if changed
        if (!existing.getVenueId().equals(dto.getVenueId())) {
            fetchVenue(dto.getVenueId());
        }
        if (!existing.getOrganizerId().equals(dto.getOrganizerId())) {
            fetchOrganizer(dto.getOrganizerId());
        }

        existing.setTitle(dto.getTitle());
        existing.setDescription(dto.getDescription());
        existing.setEventDate(dto.getEventDate());
        existing.setVenueId(dto.getVenueId());
        existing.setOrganizerId(dto.getOrganizerId());
        existing.setEventTime(dto.getEventTime());
        existing.setStatus(dto.getStatus());
        existing.setMaxAttendees(dto.getMaxAttendees());

        return eventRepository.save(existing);
    }

    // DELETE EVENT
    public void deleteEvent(String id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }

    // Helper methods to call external services

    private VenueDTO fetchVenue(Long venueId) {
        try {
            ResponseEntity<VenueDTO> response = restTemplate.getForEntity(
                    venueServiceUri + "/" + venueId, VenueDTO.class);
            if (response.getBody() == null) throw new RuntimeException("Venue not found");
            return response.getBody();
        } catch (RestClientException ex) {
            throw new RuntimeException("Failed to fetch venue: " + ex.getMessage());
        }
    }

    private UserDTO fetchOrganizer(Long organizerId) {
        try {
            ResponseEntity<UserDTO> response = restTemplate.getForEntity(
                    userServiceUri + "/" + organizerId, UserDTO.class);
            if (response.getBody() == null) throw new RuntimeException("Organizer not found");
            return response.getBody();
        } catch (RestClientException ex) {
            throw new RuntimeException("Failed to fetch organizer: " + ex.getMessage());
        }
    }
}
