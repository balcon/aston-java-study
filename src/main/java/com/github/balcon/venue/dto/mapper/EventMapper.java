package com.github.balcon.venue.dto.mapper;

import com.github.balcon.venue.dto.EventReadDto;
import com.github.balcon.venue.dto.EventWriteDto;
import com.github.balcon.venue.entity.Event;
import org.mapstruct.Mapper;

@Mapper
public interface EventMapper {
    EventReadDto toDto(Event event);

    Event toEntity(EventWriteDto eventWriteDto);
}
