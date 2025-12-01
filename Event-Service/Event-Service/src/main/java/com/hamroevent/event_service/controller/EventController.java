package com.hamroevent.event_service.controller;


import java.util.List;

import com.hamroevent.event_service.dto.EventDetailsDTO;
import com.hamroevent.event_service.dto.EventRequestDTO;
import com.hamroevent.event_service.entity.Event;
import com.hamroevent.event_service.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

//    @GetMapping("/{id}")
//    public EventDetailsDTO getEventDetails(@PathVariable String id) {
//        return eventService.getEventDetails(id);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable String id) {
        EventDetailsDTO details = eventService.getEventDetails(id);
        return ResponseEntity.ok(details);
    }


    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody EventRequestDTO eventRequestDTO) {
        Event createdEvent = eventService.createEvent(eventRequestDTO);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable String id, @RequestBody EventRequestDTO eventRequestDTO) {
        return eventService.updateEvent(id, eventRequestDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
