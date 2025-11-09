package com.hamroevent.Event_Service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String  eventDate;

    @OneToOne
    @JoinColumn(name = "venue_id", referencedColumnName = "id")
    private Venue venue; // One-to-one association with Venue

}
