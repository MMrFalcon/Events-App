package com.falcon.events.service.mapper;

import com.falcon.events.domain.EventAttendance;
import com.falcon.events.service.dto.EventAttendanceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link EventAttendance} and its DTO {@link EventAttendanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {EventMapper.class, UserMapper.class})
public interface EventAttendanceMapper extends EntityMapper<EventAttendanceDTO, EventAttendance> {

    @Mapping(source = "event", target = "eventDTO")
    @Mapping(source = "user", target = "userDTO")
    EventAttendanceDTO toDto(EventAttendance eventAttendance);

    @Mapping(source = "eventDTO", target = "event")
    @Mapping(source = "userDTO", target = "user")
    EventAttendance toEntity(EventAttendanceDTO eventAttendanceDTO);

    default EventAttendance fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventAttendance eventAttendance = new EventAttendance();
        eventAttendance.setId(id);
        return eventAttendance;
    }
}
