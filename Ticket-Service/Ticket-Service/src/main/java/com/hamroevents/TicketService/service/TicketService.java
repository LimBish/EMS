package com.hamroevents.TicketService.service;

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
    private final RestTemplate restTemplate;

    private final TicketRepository ticketRepository;

    @Value("${user.service.uri}")
    private String userServiceUri;

    @Value("${event.service.uri}")
    private String eventServiceUri;

    @Autowired
    public TicketService(RestTemplate restTemplate, TicketRepository ticketRepository) {
        this.restTemplate = restTemplate;
        this.ticketRepository = ticketRepository;
    }

    // Get all tickets
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    // Get ticket by ID
//    public Ticket getTicketById(Long id) {
//        return ticketRepository.findById(id).orElse(null);
//    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
    }



    // Create ticket with validation from User and Event Services
    public Ticket createTicket(Ticket ticket) {
        // Validate User
        ResponseEntity<UserDTO> userResponse =
                restTemplate.getForEntity(userServiceUri + ticket.getUserId(), UserDTO.class);
        if (userResponse.getStatusCode() != HttpStatus.OK || userResponse.getBody() == null) {
            throw new RuntimeException("User not found in User Service");
        }

        // Validate Event
        ResponseEntity<EventDTO> eventResponse =
                restTemplate.getForEntity(eventServiceUri + ticket.getEventId(), EventDTO.class);
        if (eventResponse.getStatusCode() != HttpStatus.OK || eventResponse.getBody() == null) {
            throw new RuntimeException("Event not found in Event Service");
        }

        return ticketRepository.save(ticket);
    }

    // Update ticket with revalidation
    public Ticket updateTicket(Long id, Ticket details) {
        Ticket existing = getTicketById(id);
        if (existing == null) {
            throw new RuntimeException("Ticket not found");
        }

        // Revalidate User if changed
        if (!existing.getUserId().equals(details.getUserId())) {
            ResponseEntity<UserDTO> userResponse =
                    restTemplate.getForEntity(userServiceUri + details.getUserId(), UserDTO.class);
            if (userResponse.getStatusCode() != HttpStatus.OK || userResponse.getBody() == null) {
                throw new RuntimeException("Invalid User ID");
            }
        }

        // Revalidate Event if changed
        if (!existing.getEventId().equals(details.getEventId())) {
            ResponseEntity<EventDTO> eventResponse =
                    restTemplate.getForEntity(eventServiceUri + details.getEventId(), EventDTO.class);
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
