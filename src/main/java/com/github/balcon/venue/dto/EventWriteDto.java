package com.github.balcon.venue.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventWriteDto(String name,
                            LocalDateTime dateTime) {
}
