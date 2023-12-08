package com.github.balcon.venue.dto;

import lombok.Builder;

/**
 * DTO of {@link com.github.balcon.venue.entity.Musician}.
 * Exclude {@link com.github.balcon.venue.entity.Band}.
 *
 * @author Konstantin Balykov
 */

@Builder
public record MusicianDto(int id,
                          String name,
                          String role) {
}
