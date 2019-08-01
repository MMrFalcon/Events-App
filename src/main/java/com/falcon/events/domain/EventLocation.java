package com.falcon.events.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EventLocation.
 */
@Entity
@Table(name = "event_location")
public class EventLocation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "event_day_of_week")
    private Integer eventDayOfWeek;

    @OneToMany(mappedBy = "eventLocation")
    private Set<Event> events = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public EventLocation locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getEventDayOfWeek() {
        return eventDayOfWeek;
    }

    public EventLocation eventDayOfWeek(Integer eventDayOfWeek) {
        this.eventDayOfWeek = eventDayOfWeek;
        return this;
    }

    public void setEventDayOfWeek(Integer eventDayOfWeek) {
        this.eventDayOfWeek = eventDayOfWeek;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public EventLocation events(Set<Event> events) {
        this.events = events;
        return this;
    }

    public EventLocation addEvent(Event event) {
        this.events.add(event);
        event.setEventLocation(this);
        return this;
    }

    public EventLocation removeEvent(Event event) {
        this.events.remove(event);
        event.setEventLocation(null);
        return this;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventLocation)) {
            return false;
        }
        return id != null && id.equals(((EventLocation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EventLocation{" +
            "id=" + getId() +
            ", locationName='" + getLocationName() + "'" +
            ", eventDayOfWeek=" + getEventDayOfWeek() +
            "}";
    }
}
