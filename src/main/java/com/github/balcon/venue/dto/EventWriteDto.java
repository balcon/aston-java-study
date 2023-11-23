package com.github.balcon.venue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventWriteDto(String name,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
                            LocalDateTime dateTime) {
}
