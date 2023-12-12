package com.github.balcon.venue.dto;

import java.util.List;
import lombok.Builder;

/**
 * DTO returned from {@link com.github.balcon.venue.service.BandService}.
 * Include list of {@link com.github.balcon.venue.entity.Musician}.
 *
 * @author Konstantin Balykov
 */

@Builder
public record BandReadDto(int id,
                          String name,
                          List<MusicianDto> musicians) {
}
