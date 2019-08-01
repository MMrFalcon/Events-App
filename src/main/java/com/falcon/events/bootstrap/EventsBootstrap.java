package com.falcon.events.bootstrap;

import com.falcon.events.domain.Event;
import com.falcon.events.domain.EventAttendance;
import com.falcon.events.domain.EventLocation;
import com.falcon.events.domain.EventUser;
import com.falcon.events.repository.EventAttendanceRepository;
import com.falcon.events.repository.EventLocationRepository;
import com.falcon.events.repository.EventRepository;
import com.falcon.events.repository.EventUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

@Component
public class EventsBootstrap implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(EventsBootstrap.class);

    private final EventLocationRepository eventLocationRepository;
    private final EventRepository eventRepository;
    private final EventAttendanceRepository eventAttendanceRepository;
    private final EventUserRepository eventUserRepository;

    public EventsBootstrap(EventLocationRepository eventLocationRepository, EventRepository eventRepository,
                           EventAttendanceRepository eventAttendanceRepository, EventUserRepository eventUserRepository) {

        this.eventLocationRepository = eventLocationRepository;
        this.eventRepository = eventRepository;
        this.eventAttendanceRepository = eventAttendanceRepository;
        this.eventUserRepository = eventUserRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (eventLocationRepository.count() == 0) {
            log.debug("Bootstrapping data at start");
            initData();
        }else{
            log.debug("Skipping data bootstrap");
        }
    }

    private void initData() {
        EventUser eventUser = new EventUser();
        eventUser.setUsername("Geralt");
        EventLocation userHomeLocation = getEventLocation("Port Tavern", DayOfWeek.FRIDAY.getValue());
        eventUser.setHomeLocation(userHomeLocation);
        eventUserRepository.save(eventUser);

        EventLocation aleEventLocation = getEventLocation("St Pete - Yard of Ale", DayOfWeek.SUNDAY.getValue());
        Event aleEvent = getEvent(aleEventLocation);

        setEventAttendance(eventUser, aleEvent);
    }

    private void setEventAttendance(EventUser eventUser, Event event) {
        EventAttendance eventAttendance = new EventAttendance();
        eventAttendance.setAttendanceDate(LocalDate.now());
        eventAttendance.setEvent(event);
        eventAttendance.setEventUser(eventUser);
        eventAttendanceRepository.save(eventAttendance);
    }

    private Event getEvent(EventLocation eventLocation) {
        Event event = new Event();
        event.setEventCode(UUID.randomUUID().toString());
        event.setEventDate(LocalDate.now());
        eventLocation.addEvent(event);
        eventLocationRepository.save(eventLocation);
        eventRepository.save(event);
        return event;
    }

    private EventLocation getEventLocation(String locationName, int dayValue) {
        EventLocation eventLocation = new EventLocation();
        eventLocation.setLocationName(locationName);
        eventLocation.setEventDayOfWeek(dayValue);
        eventLocationRepository.save(eventLocation);
        return eventLocation;
    }
}
