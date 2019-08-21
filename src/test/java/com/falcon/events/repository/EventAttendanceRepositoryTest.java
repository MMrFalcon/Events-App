package com.falcon.events.repository;

import com.falcon.events.EventsApp;
import com.falcon.events.bootstrap.EventsBootstrap;
import com.falcon.events.domain.EventAttendance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = EventsApp.class)
class EventAttendanceRepositoryTest extends AbstractRepositoryTest {

    private final String USER_LOGIN = "geralt";

    @BeforeEach
    void setUp() {
        EventsBootstrap eventsBootstrap = new EventsBootstrap(eventLocationRepository, eventRepository,
            eventAttendanceRepository, userRepository, passwordEncoder, authorityRepository);
    }

    @Test
    void findEventAttendanceByUser_Login() {
        List<EventAttendance> foundEvents = eventAttendanceRepository.findEventAttendanceByUser_Login(USER_LOGIN);

        assertNotNull(foundEvents);
        assertEquals(1, foundEvents.size());

    }
}
