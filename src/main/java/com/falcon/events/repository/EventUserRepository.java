package com.falcon.events.repository;

import com.falcon.events.domain.EventUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EventUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventUserRepository extends JpaRepository<EventUser, Long> {

}
