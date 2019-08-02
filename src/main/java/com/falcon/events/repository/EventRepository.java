package com.falcon.events.repository;

import com.falcon.events.domain.Event;
import com.falcon.events.domain.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;


/**
 * Spring Data  repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventLocationAndEventDate(EventLocation eventLocation, LocalDate date);
}
