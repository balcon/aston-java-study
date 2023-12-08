package com.github.balcon.venue.dto;

import lombok.Builder;

/**
 * DTO of {@link com.github.balcon.venue.entity.Band}.
 * Exclude list of {@link com.github.balcon.venue.entity.Musician}.
 *
 * @author Konstantin Balykov
 */

@Builder
public record BandDto(int id,
                      String name) {
}
