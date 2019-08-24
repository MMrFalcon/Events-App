package com.falcon.events.service.impl;

import com.falcon.events.domain.Event;
import com.falcon.events.repository.EventRepository;
import com.falcon.events.service.EventService;
import com.falcon.events.service.dto.EventDTO;
import com.falcon.events.service.dto.EventLocationDTO;
import com.falcon.events.service.mapper.EventLocationMapper;
import com.falcon.events.service.mapper.EventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Event}.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService {

    private final Logger log = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    private final EventLocationMapper eventLocationMapper;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, EventLocationMapper eventLocationMapper) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.eventLocationMapper = eventLocationMapper;
    }

    /**
     * Save a event.
     *
     * @param eventDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventDTO save(EventDTO eventDTO) {
        log.debug("Request to save Event : {}", eventDTO);
        Event event = eventMapper.toEntity(eventDTO);
        event = eventRepository.save(event);
        return eventMapper.toDto(event);
    }

    /**
     * Get all the events.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Events");
        return eventRepository.findAll(pageable)
            .map(eventMapper::toDto);
    }


    /**
     * Get one event by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventDTO> findOne(Long id) {
        log.debug("Request to get Event : {}", id);
        return eventRepository.findById(id)
            .map(eventMapper::toDto);
    }

    /**
     * Delete the event by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Event : {}", id);
        eventRepository.deleteById(id);
    }

    /**
     *
     * @param eventLocation event location entity
     * @param eventDate date of event
     * @return Option EventDTO
     */
    @Override
    public Optional<EventDTO> getEventByLocationAndEventDate(EventLocationDTO eventLocation, LocalDate eventDate) {
       Optional<Event> optionalEvent =  eventRepository.findByEventLocationAndEventDate(eventLocationMapper.toEntity(eventLocation), eventDate);
        return optionalEvent.map(eventMapper::toDto);
    }
}
