package com.github.balcon.venue.dto.mapper;

import com.github.balcon.venue.dto.EventReadDto;
import com.github.balcon.venue.dto.EventWriteDto;
import com.github.balcon.venue.entity.Event;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for {@link Event}.
 *
 * @author Konstantin Balykov
 */

@Mapper
public interface EventMapper {
  EventReadDto toDto(Event event);

  Event toEntity(EventWriteDto eventWriteDto);
}
