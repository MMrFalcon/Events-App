package com.falcon.events.repository;

import com.falcon.events.EventsApp;
import com.falcon.events.bootstrap.EventsBootstrap;
import com.falcon.events.domain.Event;
import com.falcon.events.domain.EventLocation;
import com.falcon.events.web.rest.EventResourceIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = EventsApp.class)
public class EventRepositoryTestIT extends AbstractRepositoryTest {

    private static final String EVENT_LOCATION_NAME = "St Pete - Yard of Ale";
    private static final String EVENT_CODE = "random-code-for-event";

    @BeforeEach
    public void setup() {
        EventsBootstrap eventsBootstrap = new EventsBootstrap(eventLocationRepository, eventRepository,
            eventAttendanceRepository, userRepository, passwordEncoder, authorityRepository);
    }

    @Test
    public void findFirstByEventLocationAndEventDate() {

        Optional<EventLocation> foundEventLocation = eventLocationRepository.findFirstByLocationName(EVENT_LOCATION_NAME);

        Optional<Event> foundEvent = eventRepository.findByEventLocationAndEventDate(foundEventLocation.get(), LocalDate.now());

        assertNotNull(foundEvent);
    }

    @Test
    public void findAllByEventCode() {
       Event createdEvent =  EventResourceIT.createEntity(entityManager);
       eventRepository.save(createdEvent);

       List<Event> foundEvent  = eventRepository.findAllByEventCode(createdEvent.getEventCode());

       assertEquals(createdEvent.getEventCode(), foundEvent.get(0).getEventCode());
    }
}
