package com.falcon.events.repository;

import com.falcon.events.domain.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the EventLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {
    List<EventLocation> findAllByEventDayOfWeek(Integer dayOfWeek);
    Optional<EventLocation> findFirstByLocationName(String locationName);
    List<EventLocation> findAllByLocationName(String locationName);
}
