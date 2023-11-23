package com.github.balcon.venue.dto.mapper;

import com.github.balcon.venue.dto.BandReadDto;
import com.github.balcon.venue.dto.BandWriteDto;
import com.github.balcon.venue.entity.Band;
import org.mapstruct.Mapper;

@Mapper
public interface BandMapper {
    BandReadDto toDto(Band band);

    Band toEntity(BandWriteDto bandWriteDto);
}
