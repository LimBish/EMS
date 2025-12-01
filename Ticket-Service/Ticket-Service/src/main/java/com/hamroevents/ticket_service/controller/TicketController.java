package com.hamroevents.ticket_service.controller;

import com.hamroevents.ticket_service.entity.Ticket;
import com.hamroevents.ticket_service.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() { return ticketService.getAllTickets(); }

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable Long id) { return ticketService.getTicketById(id); }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        log.info("entering createTicket.");
        return ticketService.createTicket(ticket); }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) { return ticketService.updateTicket(id, ticket); }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) { ticketService.deleteTicket(id); }
}

