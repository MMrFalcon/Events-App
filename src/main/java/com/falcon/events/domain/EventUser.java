package com.falcon.events.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EventUser.
 */
@Entity
@Table(name = "event_user")
public class EventUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToOne
    @JoinColumn(unique = true)
    private EventLocation homeLocation;

    @OneToMany(mappedBy = "eventUser")
    private Set<EventAttendance> eventAttendances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public EventUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public EventLocation getHomeLocation() {
        return homeLocation;
    }

    public EventUser homeLocation(EventLocation eventLocation) {
        this.homeLocation = eventLocation;
        return this;
    }

    public void setHomeLocation(EventLocation eventLocation) {
        this.homeLocation = eventLocation;
    }

    public Set<EventAttendance> getEventAttendances() {
        return eventAttendances;
    }

    public EventUser eventAttendances(Set<EventAttendance> eventAttendances) {
        this.eventAttendances = eventAttendances;
        return this;
    }

    public EventUser addEventAttendance(EventAttendance eventAttendance) {
        this.eventAttendances.add(eventAttendance);
        eventAttendance.setEventUser(this);
        return this;
    }

    public EventUser removeEventAttendance(EventAttendance eventAttendance) {
        this.eventAttendances.remove(eventAttendance);
        eventAttendance.setEventUser(null);
        return this;
    }

    public void setEventAttendances(Set<EventAttendance> eventAttendances) {
        this.eventAttendances = eventAttendances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventUser)) {
            return false;
        }
        return id != null && id.equals(((EventUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EventUser{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            "}";
    }
}