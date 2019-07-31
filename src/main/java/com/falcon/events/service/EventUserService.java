package com.falcon.events.service;

import com.falcon.events.service.dto.EventUserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.falcon.events.domain.EventUser}.
 */
public interface EventUserService {

    /**
     * Save a eventUser.
     *
     * @param eventUserDTO the entity to save.
     * @return the persisted entity.
     */
    EventUserDTO save(EventUserDTO eventUserDTO);

    /**
     * Get all the eventUsers.
     *
     * @return the list of entities.
     */
    List<EventUserDTO> findAll();


    /**
     * Get the "id" eventUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventUserDTO> findOne(Long id);

    /**
     * Delete the "id" eventUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
