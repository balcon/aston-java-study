package com.github.balcon.venue.dto;

import lombok.Builder;

@Builder
public record MusicianReadDto(int id,
                              String name,
                              String role,
                              BandDto band) {
}
