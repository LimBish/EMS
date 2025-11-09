package com.hamroevents.TicketService.controller;

import com.hamroevents.TicketService.entity.Ticket;
import com.hamroevents.TicketService.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Ticket createTicket(@RequestBody Ticket ticket) { return ticketService.createTicket(ticket); }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) { return ticketService.updateTicket(id, ticket); }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable Long id) { ticketService.deleteTicket(id); }
}

