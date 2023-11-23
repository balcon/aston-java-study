package com.github.balcon.venue.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record BandReadDto(int id,
                          String name,
                          List<MusicianDto> musicians) {
}
