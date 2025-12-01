package com.hamroevents.ticket_service.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {
    private String id;

    private String title;
    private String description;
    private Long venueId;
    private LocalDateTime eventDate;
    private Long organizerId;
}
