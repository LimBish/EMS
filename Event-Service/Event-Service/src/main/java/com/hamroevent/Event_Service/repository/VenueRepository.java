package com.hamroevent.Event_Service.repository;


import com.hamroevent.Event_Service.entity.Event;
import com.hamroevent.Event_Service.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {
}
