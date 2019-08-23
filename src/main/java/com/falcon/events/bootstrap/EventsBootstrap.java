package com.falcon.events.bootstrap;

import com.falcon.events.domain.Event;
import com.falcon.events.domain.EventAttendance;
import com.falcon.events.domain.EventLocation;
import com.falcon.events.domain.User;
import com.falcon.events.repository.*;
import com.falcon.events.security.AuthoritiesConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * Class for loading data at application start
 * Connected with integration tests! Look out for changes
 */
@Component
public class EventsBootstrap implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(EventsBootstrap.class);

    private final EventLocationRepository eventLocationRepository;
    private final EventRepository eventRepository;
    private final EventAttendanceRepository eventAttendanceRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

    public EventsBootstrap(EventLocationRepository eventLocationRepository, EventRepository eventRepository,
                           EventAttendanceRepository eventAttendanceRepository, UserRepository userRepository,
                           PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {

        this.eventLocationRepository = eventLocationRepository;
        this.eventRepository = eventRepository;
        this.eventAttendanceRepository = eventAttendanceRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
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
        User eventUser = new User();
        eventUser.setFirstName("Geralt");
        eventUser.setLastName("of Rivia");
        eventUser.setLogin("Geralt");
        eventUser.setPassword(passwordEncoder.encode("user"));
        eventUser.setEmail("mail@mail.com");
        eventUser.getAuthorities().add(authorityRepository.findByName(AuthoritiesConstants.ORGANIZER)
            .orElseThrow(RuntimeException::new));
        eventUser.getAuthorities().add(authorityRepository.findByName(AuthoritiesConstants.MEMBER)
            .orElseThrow(RuntimeException::new));
        eventUser.setActivated(true);
        EventLocation userHomeLocation = getEventLocation("Port Tavern", DayOfWeek.FRIDAY.getValue());
        eventUser.setHomeLocation(userHomeLocation);
        userRepository.save(eventUser);

        //Events Creation
        EventLocation aleEventLocation = getEventLocation("St Pete - Yard of Ale", DayOfWeek.SUNDAY.getValue());
        Event aleEvent = getEvent(aleEventLocation);

        EventLocation secondAleLocation  = getEventLocation("St Pete - Yard of Ale", DayOfWeek.SATURDAY.getValue());
        getEvent(secondAleLocation);
        //End of Events Creation

        setEventAttendance(eventUser, aleEvent);

        User eventMember = new User();
        eventMember.setFirstName("Jack");
        eventMember.setLastName("Tester");
        eventMember.setLogin("Jack");
        eventMember.setPassword(passwordEncoder.encode("user"));
        eventMember.setEmail("tester@mail.com");
        eventMember.getAuthorities().add(authorityRepository.findByName(AuthoritiesConstants.MEMBER)
            .orElseThrow(RuntimeException::new));
        eventMember.setActivated(true);
        EventLocation memberHomeLocation = getEventLocation("Tortuga", DayOfWeek.MONDAY.getValue());
        eventMember.setHomeLocation(memberHomeLocation);
        userRepository.save(eventMember);

        addMissingUserHomeLocation();
    }

    private void setEventAttendance(User eventUser, Event event) {
        EventAttendance eventAttendance = new EventAttendance();
        eventAttendance.setAttendanceDate(LocalDate.now());
        eventAttendance.setEvent(event);
        eventAttendance.setUser(eventUser);
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
        Optional<EventLocation> optionalEventLocation = eventLocationRepository.findFirstByLocationName(locationName);

        if (optionalEventLocation.isPresent()) {
            if (optionalEventLocation.get().getEventDayOfWeek() == dayValue) {
                return optionalEventLocation.get();
            } else {
                return createNewEventLocation(locationName, dayValue);
            }
        } else {
            return createNewEventLocation(locationName, dayValue);
        }
    }

    private EventLocation createNewEventLocation(String locationName, int dayValue) {
        EventLocation eventLocation = new EventLocation();
        eventLocation.setLocationName(locationName);
        eventLocation.setEventDayOfWeek(dayValue);
        eventLocationRepository.save(eventLocation);
        return eventLocation;
    }

    private void addMissingUserHomeLocation() {
        int dayOfWeek = 2;
        for (User user: userRepository.findAll()) {
            if (user.getHomeLocation() == null) {
                EventLocation eventLocation = getEventLocation("Port Tavern", dayOfWeek);
                user.setHomeLocation(eventLocation);
                userRepository.save(user);
                ++dayOfWeek;
            }
        }
    }
}
