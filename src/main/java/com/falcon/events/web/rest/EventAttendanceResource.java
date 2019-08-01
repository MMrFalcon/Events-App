package com.falcon.events.web.rest;

import com.falcon.events.service.EventAttendanceService;
import com.falcon.events.service.dto.EventAttendanceDTO;
import com.falcon.events.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.falcon.events.domain.EventAttendance}.
 */
@RestController
@RequestMapping("/api")
public class EventAttendanceResource {

    private final Logger log = LoggerFactory.getLogger(EventAttendanceResource.class);

    private static final String ENTITY_NAME = "eventAttendance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventAttendanceService eventAttendanceService;

    public EventAttendanceResource(EventAttendanceService eventAttendanceService) {
        this.eventAttendanceService = eventAttendanceService;
    }

    /**
     * {@code POST  /event-attendances} : Create a new eventAttendance.
     *
     * @param eventAttendanceDTO the eventAttendanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventAttendanceDTO, or with status {@code 400 (Bad Request)} if the eventAttendance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-attendances")
    public ResponseEntity<EventAttendanceDTO> createEventAttendance(@RequestBody EventAttendanceDTO eventAttendanceDTO) throws URISyntaxException {
        log.debug("REST request to save EventAttendance : {}", eventAttendanceDTO);
        if (eventAttendanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventAttendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventAttendanceDTO result = eventAttendanceService.save(eventAttendanceDTO);
        return ResponseEntity.created(new URI("/api/event-attendances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-attendances} : Updates an existing eventAttendance.
     *
     * @param eventAttendanceDTO the eventAttendanceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventAttendanceDTO,
     * or with status {@code 400 (Bad Request)} if the eventAttendanceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventAttendanceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-attendances")
    public ResponseEntity<EventAttendanceDTO> updateEventAttendance(@RequestBody EventAttendanceDTO eventAttendanceDTO) throws URISyntaxException {
        log.debug("REST request to update EventAttendance : {}", eventAttendanceDTO);
        if (eventAttendanceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventAttendanceDTO result = eventAttendanceService.save(eventAttendanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventAttendanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-attendances} : get all the eventAttendances.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventAttendances in body.
     */
    @GetMapping("/event-attendances")
    public ResponseEntity<List<EventAttendanceDTO>> getAllEventAttendances(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of EventAttendances");
        Page<EventAttendanceDTO> page = eventAttendanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-attendances/:id} : get the "id" eventAttendance.
     *
     * @param id the id of the eventAttendanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventAttendanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-attendances/{id}")
    public ResponseEntity<EventAttendanceDTO> getEventAttendance(@PathVariable Long id) {
        log.debug("REST request to get EventAttendance : {}", id);
        Optional<EventAttendanceDTO> eventAttendanceDTO = eventAttendanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventAttendanceDTO);
    }

    /**
     * {@code DELETE  /event-attendances/:id} : delete the "id" eventAttendance.
     *
     * @param id the id of the eventAttendanceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-attendances/{id}")
    public ResponseEntity<Void> deleteEventAttendance(@PathVariable Long id) {
        log.debug("REST request to delete EventAttendance : {}", id);
        eventAttendanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
