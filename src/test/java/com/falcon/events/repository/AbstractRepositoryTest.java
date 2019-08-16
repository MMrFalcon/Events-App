package com.falcon.events.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

abstract class AbstractRepositoryTest {

    @Autowired
    EventLocationRepository eventLocationRepository;

    @Autowired
    EventAttendanceRepository eventAttendanceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;
}
