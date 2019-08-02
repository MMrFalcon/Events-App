package com.falcon.events.repository;

import org.springframework.beans.factory.annotation.Autowired;

abstract class AbstractRepositoryTest {

    @Autowired
    EventLocationRepository eventLocationRepository;

    @Autowired
    EventAttendanceRepository eventAttendanceRepository;

    @Autowired
    EventUserRepository eventUserRepository;

    @Autowired
    EventRepository eventRepository;


}
