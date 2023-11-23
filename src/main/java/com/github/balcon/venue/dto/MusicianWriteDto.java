package com.github.balcon.venue.dto;

import lombok.Builder;

@Builder
public record MusicianWriteDto(String name,
                               String role,
                               Integer bandId) {
}
