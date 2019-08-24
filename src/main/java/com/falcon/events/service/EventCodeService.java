package com.falcon.events.service;

import com.falcon.events.domain.Event;
import com.falcon.events.domain.EventLocation;
import com.falcon.events.repository.EventLocationRepository;
import com.falcon.events.repository.EventRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for scheduling events code creation for new locations
 */
@Service
public class EventCodeService {

    private final Logger log = LoggerFactory.getLogger(EventCodeService.class);

    private final EventLocationRepository eventLocationRepository;
    private final EventRepository eventRepository;

    public EventCodeService(EventLocationRepository eventLocationRepository, EventRepository eventRepository) {
        this.eventLocationRepository = eventLocationRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * Generating Event Code for Location with today day of week
     * if event with today date does not exist in the system.
     */
//    @Scheduled(cron = "0 0 * * * ?") //run once per hour, at top of hour
    @Scheduled(cron = "0 * * * * ?") //run once per min
    public void generateEventsCodeForLocations() {
        log.debug("Generating events");

        List<EventLocation> eventLocations = eventLocationRepository.findAllByEventDayOfWeek(
            LocalDate.now().getDayOfWeek().getValue());

        eventLocations.forEach(eventLocation -> {
            log.debug("Checking event for location: " + eventLocation.toString());
            Optional<Event> existingEvent = eventRepository.findByEventLocationAndEventDate(eventLocation, LocalDate.now());

            if (!existingEvent.isPresent()) {
                log.debug("Event not found. Creation of new Event");

                Event event = new Event();
                event.setEventLocation(eventLocation);
                event.setEventDate(LocalDate.now());
                event.setEventCode(RandomStringUtils.randomAlphanumeric(10).toUpperCase());
                eventRepository.save(event);

                log.debug("Created new event: " + event.toString());
            } else {
                log.debug("Event already exist");
            }
        });
    }

    @Scheduled(cron = "0 * * * * ?")
    public void generateEventsCodeForNewEvents() {
        log.debug("Checking for newly added Events");

        List<Event> eventsWithoutCode = eventRepository.findAllByEventCode(null);

        eventsWithoutCode.forEach(event -> {
            log.debug("Generating event code for {}", event);
            event.setEventCode(UUID.randomUUID().toString());
            eventRepository.save(event);
            log.debug("New event code was generated for event {}", event);
        });
    }
}
