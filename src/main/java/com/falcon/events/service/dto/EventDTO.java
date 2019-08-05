package com.falcon.events.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.falcon.events.domain.Event} entity.
 */
public class EventDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate eventDate;

    private String eventCode;


    private EventLocationDTO eventLocationDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public EventLocationDTO getEventLocationDTO() {
        return eventLocationDTO;
    }

    public void setEventLocationDTO(EventLocationDTO eventLocationDTO) {
        this.eventLocationDTO = eventLocationDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventDTO eventDTO = (EventDTO) o;
        if (eventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventDTO{" +
            "id=" + getId() +
            ", eventDate='" + getEventDate() + "'" +
            ", eventCode='" + getEventCode() + "'" +
            ", eventLocation=" + getEventLocationDTO() +
            "}";
    }
}
