package com.github.balcon.venue.dto.mapper;

import com.github.balcon.venue.dto.MusicianReadDto;
import com.github.balcon.venue.dto.MusicianWriteDto;
import com.github.balcon.venue.entity.Musician;
import org.mapstruct.Mapper;

/**
 * MapStruct mapper for {@link Musician}.
 *
 * @author Konstantin Balykov
 */

@Mapper
public interface MusicianMapper {
  MusicianReadDto toDto(Musician musician);

  Musician toEntity(MusicianWriteDto musicianWriteDto);
}
