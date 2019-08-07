package com.falcon.events.service.mapper;

import com.falcon.events.domain.Event;
import com.falcon.events.service.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Event} and its DTO {@link EventDTO}.
 */
@Mapper(componentModel = "spring", uses = {EventLocationMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "eventLocation", target = "eventLocationDTO")
    EventDTO toDto(Event event);

    @Mapping(source = "eventLocationDTO", target = "eventLocation")
    @Mapping(target = "eventAttendances", ignore = true)
    @Mapping(target = "removeEventAttendance", ignore = true)
    Event toEntity(EventDTO eventDTO);

    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
