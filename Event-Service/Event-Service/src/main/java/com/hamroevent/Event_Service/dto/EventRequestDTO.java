package com.hamroevent.Event_Service.dto;


import com.hamroevent.Event_Service.entity.Venue;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequestDTO {

    private Long id;

    private String title;
    private String description;
    private LocalDateTime eventDate;


    private Venue venue; // One-to-one association with Venue

}
