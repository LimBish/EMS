package com.hamroevent.event_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class EventRequestDTO {

    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime eventDate;

    private Long venueId;
    private Long organizerId;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime eventTime;
    private String status;
    private Integer maxAttendees;
}

