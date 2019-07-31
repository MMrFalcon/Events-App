package com.falcon.events.web.rest;

import com.falcon.events.service.EventUserService;
import com.falcon.events.service.dto.EventUserDTO;
import com.falcon.events.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.falcon.events.domain.EventUser}.
 */
@RestController
@RequestMapping("/api")
public class EventUserResource {

    private final Logger log = LoggerFactory.getLogger(EventUserResource.class);

    private static final String ENTITY_NAME = "eventUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventUserService eventUserService;

    public EventUserResource(EventUserService eventUserService) {
        this.eventUserService = eventUserService;
    }

    /**
     * {@code POST  /event-users} : Create a new eventUser.
     *
     * @param eventUserDTO the eventUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventUserDTO, or with status {@code 400 (Bad Request)} if the eventUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-users")
    public ResponseEntity<EventUserDTO> createEventUser(@RequestBody EventUserDTO eventUserDTO) throws URISyntaxException {
        log.debug("REST request to save EventUser : {}", eventUserDTO);
        if (eventUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new eventUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventUserDTO result = eventUserService.save(eventUserDTO);
        return ResponseEntity.created(new URI("/api/event-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-users} : Updates an existing eventUser.
     *
     * @param eventUserDTO the eventUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventUserDTO,
     * or with status {@code 400 (Bad Request)} if the eventUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-users")
    public ResponseEntity<EventUserDTO> updateEventUser(@RequestBody EventUserDTO eventUserDTO) throws URISyntaxException {
        log.debug("REST request to update EventUser : {}", eventUserDTO);
        if (eventUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventUserDTO result = eventUserService.save(eventUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, eventUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-users} : get all the eventUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventUsers in body.
     */
    @GetMapping("/event-users")
    public List<EventUserDTO> getAllEventUsers() {
        log.debug("REST request to get all EventUsers");
        return eventUserService.findAll();
    }

    /**
     * {@code GET  /event-users/:id} : get the "id" eventUser.
     *
     * @param id the id of the eventUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-users/{id}")
    public ResponseEntity<EventUserDTO> getEventUser(@PathVariable Long id) {
        log.debug("REST request to get EventUser : {}", id);
        Optional<EventUserDTO> eventUserDTO = eventUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventUserDTO);
    }

    /**
     * {@code DELETE  /event-users/:id} : delete the "id" eventUser.
     *
     * @param id the id of the eventUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-users/{id}")
    public ResponseEntity<Void> deleteEventUser(@PathVariable Long id) {
        log.debug("REST request to delete EventUser : {}", id);
        eventUserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
