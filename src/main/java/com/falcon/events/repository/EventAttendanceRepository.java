package com.falcon.events.repository;

import com.falcon.events.domain.EventAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the EventAttendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventAttendanceRepository extends JpaRepository<EventAttendance, Long> {

    @Query("select eventAttendance from EventAttendance eventAttendance where eventAttendance.user.login = ?#{principal.username}")
    List<EventAttendance> findByUserIsCurrentUser();

    List<EventAttendance> findEventAttendanceByUser_Login(String userLogin);

}
