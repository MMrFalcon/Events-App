package com.falcon.events.repository;

import com.falcon.events.domain.EventAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EventAttendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Long> {

}
