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


    private Long eventId;

    private Long eventUserId;

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

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventUserId() {
        return eventUserId;
    }

    public void setEventUserId(Long eventUserId) {
        this.eventUserId = eventUserId;
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
            ", event=" + getEventId() +
            ", eventUser=" + getEventUserId() +
            "}";
    }
}
