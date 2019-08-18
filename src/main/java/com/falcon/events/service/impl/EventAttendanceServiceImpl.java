package com.falcon.events.service.impl;

import com.falcon.events.domain.EventAttendance;
import com.falcon.events.repository.EventAttendanceRepository;
import com.falcon.events.service.EventAttendanceService;
import com.falcon.events.service.dto.EventAttendanceDTO;
import com.falcon.events.service.mapper.EventAttendanceMapper;
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
 * Service Implementation for managing {@link EventAttendance}.
 */
@Service
@Transactional
public class EventAttendanceServiceImpl implements EventAttendanceService {

    private final Logger log = LoggerFactory.getLogger(EventAttendanceServiceImpl.class);

    private final EventAttendanceRepository eventAttendanceRepository;

    private final EventAttendanceMapper eventAttendanceMapper;

    public EventAttendanceServiceImpl(EventAttendanceRepository eventAttendanceRepository, EventAttendanceMapper eventAttendanceMapper) {
        this.eventAttendanceRepository = eventAttendanceRepository;
        this.eventAttendanceMapper = eventAttendanceMapper;
    }

    /**
     * Save a eventAttendance.
     *
     * @param eventAttendanceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EventAttendanceDTO save(EventAttendanceDTO eventAttendanceDTO) {
        log.debug("Request to save EventAttendance : {}", eventAttendanceDTO);
        EventAttendance eventAttendance = eventAttendanceMapper.toEntity(eventAttendanceDTO);
        eventAttendance = eventAttendanceRepository.save(eventAttendance);
        return eventAttendanceMapper.toDto(eventAttendance);
    }

    /**
     * Get all the eventAttendances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EventAttendanceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EventAttendances");
        return eventAttendanceRepository.findAll(pageable)
            .map(eventAttendanceMapper::toDto);
    }


    /**
     * Get one eventAttendance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EventAttendanceDTO> findOne(Long id) {
        log.debug("Request to get EventAttendance : {}", id);
        return eventAttendanceRepository.findById(id)
            .map(eventAttendanceMapper::toDto);
    }

    /**
     * Delete the eventAttendance by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventAttendance : {}", id);
        eventAttendanceRepository.deleteById(id);
    }
//TODO test me and write some doc
    @Override
    public List<EventAttendanceDTO> findByUserLogin(String userLogin) {
        log.debug("Request to get EventAttendances by user login");
        return eventAttendanceRepository.findEventAttendanceByUser_Login(userLogin)
            .stream().map(eventAttendanceMapper::toDto).collect(Collectors.toList());
    }
}
