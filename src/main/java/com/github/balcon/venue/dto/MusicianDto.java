package com.github.balcon.venue.dto;

import lombok.Builder;

@Builder
public record MusicianDto(int id,
                          String name,
                          String role) {
}
