package com.github.balcon.venue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public record EventReadDto(int id,
                           String name,
                           @JsonFormat(pattern="yyyy-MM-dd HH:mm")
                           LocalDateTime dateTime,
                           List<BandDto> bands) {
}
