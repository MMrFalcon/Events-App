package com.falcon.events.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.falcon.events.domain.EventLocation} entity.
 */
public class EventLocationDTO implements Serializable {

    private Long id;

    @NotNull
    private String locationName;

    private Integer eventDayOfWeek;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getEventDayOfWeek() {
        return eventDayOfWeek;
    }

    public void setEventDayOfWeek(Integer eventDayOfWeek) {
        this.eventDayOfWeek = eventDayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventLocationDTO eventLocationDTO = (EventLocationDTO) o;
        if (eventLocationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventLocationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventLocationDTO{" +
            "id=" + getId() +
            ", locationName='" + getLocationName() + "'" +
            ", eventDayOfWeek=" + getEventDayOfWeek() +
            "}";
    }
}
