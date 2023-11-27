package com.github.balcon.venue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record BandWriteDto(@NotBlank String name) {
}
