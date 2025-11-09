package com.hamroevents.TicketService.service;

import com.hamroevents.TicketService.entity.Ticket;
import com.hamroevents.TicketService.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class TicketService {
//
//    @Autowired
//    private TicketRepository ticketRepository;
//
//    public List<Ticket> getAllTickets() { return ticketRepository.findAll(); }
//
//    public Ticket getTicketById(Long id) { return ticketRepository.findById(id).orElse(null); }
//
//    public Ticket createTicket(Ticket ticket) { return ticketRepository.save(ticket); }
//
//    public Ticket updateTicket(Long id, Ticket details) {
//        Ticket t = getTicketById(id);
//        if (t == null) throw new RuntimeException("Ticket not found");
//        t.setEventId(details.getEventId());
//        t.setUserId(details.getUserId());
//        t.setQuantity(details.getQuantity());
//        return ticketRepository.save(t);
//    }
//
//    public void deleteTicket(Long id) { ticketRepository.deleteById(id); }
//}


import com.hamroevents.TicketService.entity.Ticket;
import com.hamroevents.TicketService.entity.UserDTO;
import com.hamroevents.TicketService.entity.EventDTO;
import com.hamroevents.TicketService.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TicketRepository ticketRepository;

    @Value("${user.service.uri}")
    private String userServiceUri;

    @Value("${event.service.uri}")
    private String eventServiceUri;

    // Get all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Get ticket by ID
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    // Create ticket with external validation
    public Ticket createTicket(Ticket ticket) {
        // Validate User
        ResponseEntity<UserDTO> userResponse = restTemplate.getForEntity(userServiceUri + ticket.getUserId(), UserDTO.class);
        if (userResponse.getStatusCode() != HttpStatus.OK || userResponse.getBody() == null) {
            throw new RuntimeException("User not found in User Service");
        }
        UserDTO user = userResponse.getBody();

        // Validate Event
        ResponseEntity<EventDTO> eventResponse = restTemplate.getForEntity(eventServiceUri + ticket.getEventId(), EventDTO.class);
        if (eventResponse.getStatusCode() != HttpStatus.OK || eventResponse.getBody() == null) {
            throw new RuntimeException("Event not found in Event Service");
        }
        EventDTO event = eventResponse.getBody();

        // Optional: Check for available seats (if required)
        // Example: if (event.getAvailableSeats() < ticket.getQuantity()) throw new RuntimeException("Not enough seats");

        return ticketRepository.save(ticket);
    }

    // Update ticket with validation
    public Ticket updateTicket(Long id, Ticket details) {
        Ticket existing = getTicketById(id);
        if (existing == null) {
            throw new RuntimeException("Ticket not found");
        }

        // Revalidate user if changed
        if (!existing.getUserId().equals(details.getUserId())) {
            ResponseEntity<UserDTO> userResponse = restTemplate.getForEntity(userServiceUri + details.getUserId(), UserDTO.class);
            if (userResponse.getStatusCode() != HttpStatus.OK || userResponse.getBody() == null) {
                throw new RuntimeException("Invalid User ID");
            }
        }

        // Revalidate event if changed
        if (!existing.getEventId().equals(details.getEventId())) {
            ResponseEntity<EventDTO> eventResponse = restTemplate.getForEntity(eventServiceUri + details.getEventId(), EventDTO.class);
            if (eventResponse.getStatusCode() != HttpStatus.OK || eventResponse.getBody() == null) {
                throw new RuntimeException("Invalid Event ID");
            }
        }

        existing.setUserId(details.getUserId());
        existing.setEventId(details.getEventId());
        existing.setQuantity(details.getQuantity());

        return ticketRepository.save(existing);
    }

    // Delete ticket
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}

