package com.falcon.events.service.impl;

import com.falcon.events.domain.EventUser;
import com.falcon.events.repository.EventUserRepository;
import com.falcon.events.service.EventUserService;
import com.falcon.events.service.dto.EventUserDTO;
import com.falcon.events.service.mapper.EventUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EventUser}.
 */
@Service
@Transactional
public class EventUserServiceImpl implements EventUserService {

    private final Logger log = LoggerFactory.getLogger(EventUserServiceImpl.class);

    private final EventUserRepository eventUserRepository;

    private final EventUserMapper eventUserMapper;

    public EventUserServiceImpl(EventUserRepository eventUserRepository, EventUserMapper eventUserMapper) {
        this.eventUserRepository = eventUserRepository;
        this.eventUserMapper = eventUserMapper;
    }

    /**
     * Save a eventUser.
     *
     * @param eventUserDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventUserDTO save(EventUserDTO eventUserDTO) {
        log.debug("Request to save EventUser : {}", eventUserDTO);
        EventUser eventUser = eventUserMapper.toEntity(eventUserDTO);
        eventUser = eventUserRepository.save(eventUser);
        return eventUserMapper.toDto(eventUser);
    }

    /**
     * Get all the eventUsers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<EventUserDTO> findAll() {
        log.debug("Request to get all EventUsers");
        return eventUserRepository.findAll().stream()
            .map(eventUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one eventUser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventUserDTO> findOne(Long id) {
        log.debug("Request to get EventUser : {}", id);
        return eventUserRepository.findById(id)
            .map(eventUserMapper::toDto);
    }

    /**
     * Delete the eventUser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventUser : {}", id);
        eventUserRepository.deleteById(id);
    }
}
