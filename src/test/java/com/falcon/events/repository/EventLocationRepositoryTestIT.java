package com.falcon.events.repository;

import com.falcon.events.EventsApp;
import com.falcon.events.bootstrap.EventsBootstrap;
import com.falcon.events.domain.EventLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(classes = EventsApp.class)
class EventLocationRepositoryTestIT extends AbstractRepositoryTest {

    private final String FIRST_EVENT_LOCATION_NAME = "St Pete - Yard of Ale";
    private final String SECOND_EVENT_LOCATION_NAME = "Port Tavern";

    @BeforeEach
    void setUp() {
        EventsBootstrap eventsBootstrap = new EventsBootstrap(eventLocationRepository, eventRepository,
            eventAttendanceRepository, userRepository, passwordEncoder, authorityRepository);
    }

    @Test
    void findAllByEventDayOfWeek() {
        List<EventLocation> fridayLocations = eventLocationRepository.findAllByEventDayOfWeek(DayOfWeek.FRIDAY.getValue());
        List<EventLocation> sundayLocations = eventLocationRepository.findAllByEventDayOfWeek(DayOfWeek.SUNDAY.getValue());

        assertEquals(1, fridayLocations.size());
        assertEquals(1, sundayLocations.size());
    }

    @Test
    void findFirstByLocationName() {
        Optional<EventLocation> firstOptionalLocation = eventLocationRepository.findFirstByLocationName(FIRST_EVENT_LOCATION_NAME);
        Optional<EventLocation> secondOptionalLocation = eventLocationRepository.findFirstByLocationName(SECOND_EVENT_LOCATION_NAME);

        assertTrue(firstOptionalLocation.isPresent());
        assertTrue(secondOptionalLocation.isPresent());

        assertEquals(FIRST_EVENT_LOCATION_NAME, firstOptionalLocation.get().getLocationName());
        assertEquals(SECOND_EVENT_LOCATION_NAME, secondOptionalLocation.get().getLocationName());
    }

    @Test
    void findAllByLocationName() {
        List<EventLocation> eventLocations = eventLocationRepository.findAllByLocationName(SECOND_EVENT_LOCATION_NAME);
        List<EventLocation> mostlyUsedEventLocation = eventLocationRepository.findAllByLocationName(FIRST_EVENT_LOCATION_NAME);

        assertEquals(5, eventLocations.size());
        assertEquals(2, mostlyUsedEventLocation.size());
    }
}
