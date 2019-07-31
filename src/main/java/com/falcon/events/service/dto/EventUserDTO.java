package com.falcon.events.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.falcon.events.domain.EventUser} entity.
 */
public class EventUserDTO implements Serializable {

    private Long id;

    private String username;


    private Long homeLocationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getHomeLocationId() {
        return homeLocationId;
    }

    public void setHomeLocationId(Long eventLocationId) {
        this.homeLocationId = eventLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventUserDTO eventUserDTO = (EventUserDTO) o;
        if (eventUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eventUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EventUserDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", homeLocation=" + getHomeLocationId() +
            "}";
    }
}
