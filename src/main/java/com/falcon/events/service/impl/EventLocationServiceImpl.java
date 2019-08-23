package com.falcon.events.service.impl;

import com.falcon.events.domain.EventLocation;
import com.falcon.events.repository.EventLocationRepository;
import com.falcon.events.service.EventLocationService;
import com.falcon.events.service.dto.EventLocationDTO;
import com.falcon.events.service.mapper.EventLocationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EventLocation}.
 */
@Service
@Transactional
public class EventLocationServiceImpl implements EventLocationService {

    private final Logger log = LoggerFactory.getLogger(EventLocationServiceImpl.class);

    private final EventLocationRepository eventLocationRepository;

    private final EventLocationMapper eventLocationMapper;

    public EventLocationServiceImpl(EventLocationRepository eventLocationRepository, EventLocationMapper eventLocationMapper) {
        this.eventLocationRepository = eventLocationRepository;
        this.eventLocationMapper = eventLocationMapper;
    }

    /**
     * Save a eventLocation.
     *
     * @param eventLocationDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventLocationDTO save(EventLocationDTO eventLocationDTO) {
        log.debug("Request to save EventLocation : {}", eventLocationDTO);
        EventLocation eventLocation = eventLocationMapper.toEntity(eventLocationDTO);
        eventLocation = eventLocationRepository.save(eventLocation);
        return eventLocationMapper.toDto(eventLocation);
    }

    /**
     * Get all the eventLocations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventLocationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventLocations");
        return eventLocationRepository.findAll(pageable)
            .map(eventLocationMapper::toDto);
    }


    /**
     * Get one eventLocation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventLocationDTO> findOne(Long id) {
        log.debug("Request to get EventLocation : {}", id);
        return eventLocationRepository.findById(id)
            .map(eventLocationMapper::toDto);
    }

    /**
     * Delete the eventLocation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventLocation : {}", id);
        eventLocationRepository.deleteById(id);
    }

    /**
     *
     * @param locationName name of event location
     * @return list of event locations DTO objects
     */
    @Override
    public List<EventLocationDTO> getAllLocationByName(String locationName) {
        log.debug("Searching for events location with name : {} ", locationName);
        return eventLocationRepository.findAllByLocationName(locationName)
            .stream().map(eventLocationMapper::toDto).collect(Collectors.toList());
    }
}
