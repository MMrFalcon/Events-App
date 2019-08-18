package com.falcon.events.service;

import com.falcon.events.service.dto.EventAttendanceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.falcon.events.domain.EventAttendance}.
 */
public interface EventAttendanceService {

    /**
     * Save a eventAttendance.
     *
     * @param eventAttendanceDTO the entity to save.
     * @return the persisted entity.
     */
    EventAttendanceDTO save(EventAttendanceDTO eventAttendanceDTO);

    /**
     * Get all the eventAttendances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventAttendanceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" eventAttendance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventAttendanceDTO> findOne(Long id);

    /**
     * Delete the "id" eventAttendance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<EventAttendanceDTO> findByUserLogin(String userLogin);
}
