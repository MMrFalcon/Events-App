package com.falcon.events.service.mapper;

import com.falcon.events.domain.EventLocation;
import com.falcon.events.service.dto.EventLocationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link EventLocation} and its DTO {@link EventLocationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EventLocationMapper extends EntityMapper<EventLocationDTO, EventLocation> {


    @Mapping(target = "events", ignore = true)
    @Mapping(target = "removeEvent", ignore = true)
    EventLocation toEntity(EventLocationDTO eventLocationDTO);

    default EventLocation fromId(Long id) {
        if (id == null) {
            return null;
        }
        EventLocation eventLocation = new EventLocation();
        eventLocation.setId(id);
        return eventLocation;
    }
}
