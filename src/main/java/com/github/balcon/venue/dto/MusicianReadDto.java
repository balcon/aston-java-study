package com.github.balcon.venue.dto;

import lombok.Builder;

/**
 * DTO returned from {@link com.github.balcon.venue.service.MusicianService}.
 * Include {@link com.github.balcon.venue.entity.Band}.
 *
 * @author Konstantin Balykov
 */


@Builder
public record MusicianReadDto(int id,
                              String name,
                              String role,
                              BandDto band) {
}
