package com.falcon.events.service;

import com.falcon.events.service.dto.EventDTO;
import com.falcon.events.service.dto.EventLocationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.falcon.events.domain.Event}.
 */
public interface EventService {

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    EventDTO save(EventDTO eventDTO);

    /**
     * Get all the events.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventDTO> findAll(Pageable pageable);


    /**
     * Get the "id" event.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventDTO> findOne(Long id);

    /**
     * Delete the "id" event.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     *
     * @param eventLocation event location entity
     * @param eventDate date of event
     * @return optional event
     */
    Optional<EventDTO> getEventByLocationAndEventDate(EventLocationDTO eventLocation, LocalDate eventDate);
}
