package com.github.balcon.venue.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EventReadDto(int id,
                           String name,
                           LocalDateTime dateTime,
                           List<BandDto> bands) {
}
