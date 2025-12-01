package com.hamroevent.event_service.entity;

//import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document

public class Event {
    @Id
    private String id;
    private String title;
    private String description;
    private LocalDateTime eventDate;

    // References to other services (store only IDs)
    private Long venueId;      // Reference to Venue-Service
    private Long organizerId;    // Reference to User-Service (the organizer)

    // Event-specific fields you might want to add
    @JsonFormat(pattern = "HH:mm")
    private LocalTime eventTime;
    private String status;       // DRAFT, PUBLISHED, CANCELLED, COMPLETED
    private Integer maxAttendees;
}