package com.falcon.events.repository;

import com.falcon.events.EventsApp;
import com.falcon.events.bootstrap.EventsBootstrap;
import com.falcon.events.domain.Event;
import com.falcon.events.domain.EventLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = EventsApp.class)
public class EventRepositoryTestIT extends AbstractRepositoryTest {

    private final String EVENT_LOCATION_NAME = "St Pete - Yard of Ale";

    @BeforeEach
    public void setup() {
        EventsBootstrap eventsBootstrap = new EventsBootstrap(eventLocationRepository, eventRepository,
            eventAttendanceRepository, userRepository, passwordEncoder, authorityRepository);
    }

    @Test
    public void findByEventLocationAndEventDate() {

        Optional<EventLocation> foundEventLocation = eventLocationRepository.findByLocationName(EVENT_LOCATION_NAME);

        Optional<Event> foundEvent = eventRepository.findByEventLocationAndEventDate(foundEventLocation.get(), LocalDate.now());

        assertNotNull(foundEvent);
    }
}
