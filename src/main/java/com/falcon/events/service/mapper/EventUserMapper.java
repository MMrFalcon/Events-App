package com.falcon.events.service.mapper;

import com.falcon.events.domain.EventUser;
import com.falcon.events.service.dto.EventUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link EventUser} and its DTO {@link EventUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {EventLocationMapper.class})
public interface EventUserMapper extends EntityMapper<EventUserDTO, EventUser> {

    @Mapping(source = "homeLocation.id", target = "homeLocationId")
    EventUserDTO toDto(EventUser eventUser);

    @Mapping(source = "homeLocationId", target = "homeLocation")
    @Mapping(target = "eventAttendances", ignore = true)
    @Mapping(target = "removeEventAttendance", ignore = true)
    EventUser toEntity(EventUserDTO eventUserDTO);

    default EventUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventUser eventUser = new EventUser();
        eventUser.setId(id);
        return eventUser;
    }
}
