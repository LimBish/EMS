package com.hamroevents.TicketService.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private Long id;

    private String title;
    private String description;
    private String venue;
    private LocalDateTime eventDate;
    private Long organizerId;
}
