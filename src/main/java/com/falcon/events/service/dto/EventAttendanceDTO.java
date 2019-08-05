package com.falcon.events.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.falcon.events.domain.EventAttendance} entity.
 */
public class EventAttendanceDTO implements Serializable {

    private Long id;

    private LocalDate attendanceDate;


    private EventDTO eventDTO;

    private EventUserDTO eventUserDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public EventDTO getEventDTO() {
        return eventDTO;
    }

    public void setEventDTO(EventDTO eventDTO) {
        this.eventDTO = eventDTO;
    }

    public EventUserDTO getEventUserDTO() {
        return eventUserDTO;
    }

    public void setEventUserDTO(EventUserDTO eventUserDTO) {
        this.eventUserDTO = eventUserDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventAttendanceDTO eventAttendanceDTO = (EventAttendanceDTO) o;
        if (eventAttendanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventAttendanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventAttendanceDTO{" +
            "id=" + getId() +
            ", attendanceDate='" + getAttendanceDate() + "'" +
            ", event=" + getEventDTO() +
            ", eventUser=" + getEventUserDTO() +
            "}";
    }
}
