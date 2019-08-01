package com.falcon.events.repository;

import com.falcon.events.domain.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EventLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Long> {

}
