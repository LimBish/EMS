package com.hamroevent.Event_Service.service;

import java.util.Date;
import java.util.List;

import com.hamroevent.Event_Service.dto.EventRequestDTO;
import com.hamroevent.Event_Service.entity.Event;
import com.hamroevent.Event_Service.entity.Venue;
import com.hamroevent.Event_Service.repository.EventRepository;
import com.hamroevent.Event_Service.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//import com.hamroevent.Event_Service.entity.UserDTO;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;

//
//
//@Service
//public class EventService {
//
//    @Autowired
//    private EventRepository eventRepository;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @Value("${user.service.uri}")
//    private String userServiceUri;
//
//    public List<Event> getAllEvents() {
//        return eventRepository.findAll();
//    }
//
//    public Event getEventById(Long id) {
//        return eventRepository.findById(id);
//    }
//
//    public Event createEvent(Event event) {
//        // Verify organizer exists in User Service
//        ResponseEntity<UserDTO> userEntity = restTemplate.getForEntity(
//                userServiceUri + event.getOrganizerId(), UserDTO.class);
//
//        if (userEntity.getStatusCode() != HttpStatus.OK || userEntity.getBody() == null) {
//            throw new RuntimeException("Cannot get user from User Service");
//        }
//
//        UserDTO user = userEntity.getBody();
//        if (user == null) {
//            throw new RuntimeException("Organizer not found");
//        }
//
//        return eventRepository.save(event);
//    }
//
//    public Event updateEvent(Long id, Event eventDetails) {
//        Event existingEvent = getEventById(id);
//        if (existingEvent == null) {
//            throw new RuntimeException("Event not found");
//        }
//
//        // Verify organizer exists
//        ResponseEntity<UserDTO> userEntity = restTemplate.getForEntity(
//                userServiceUri + eventDetails.getOrganizerId(), UserDTO.class);
//        if (userEntity.getStatusCode() != HttpStatus.OK || userEntity.getBody() == null) {
//            throw new RuntimeException("Cannot get user from User Service");
//        }
//
//        existingEvent.setTitle(eventDetails.getTitle());
//        existingEvent.setDescription(eventDetails.getDescription());
//        existingEvent.setVenue(eventDetails.getVenue());
//        existingEvent.setEventDate(eventDetails.getEventDate());
//        existingEvent.setOrganizerId(eventDetails.getOrganizerId());
//
//        return eventRepository.save(existingEvent);
//    }
//
//    public void deleteEvent(Long id) {
//        eventRepository.deleteById(id);
//    }
//}


@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private VenueRepository venueRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event createEvent(EventRequestDTO requestDTO) {

        Event event = new Event();
        event.setTitle(requestDTO.getTitle());
        event.setDescription(requestDTO.getDescription());
        event.setEventDate(new Date().toString());

        Venue venue = new Venue();
        venue.setName(requestDTO.getVenue().getName());
        venue.setAddress(requestDTO.getVenue().getAddress());
        venue.setCapacity(requestDTO.getVenue().getCapacity());

       Venue savedVenue = venueRepository.save(venue);
        event.setVenue(savedVenue);

        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event existingEvent = getEventById(id);
        if (existingEvent == null) {
            throw new RuntimeException("Event not found");
        }

        existingEvent.setTitle(eventDetails.getTitle());
        existingEvent.setDescription(eventDetails.getDescription());
        existingEvent.setVenue(eventDetails.getVenue());
        existingEvent.setEventDate(eventDetails.getEventDate());
//        existingEvent.setOrganizerId(eventDetails.getOrganizerId());

        return eventRepository.save(existingEvent);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
