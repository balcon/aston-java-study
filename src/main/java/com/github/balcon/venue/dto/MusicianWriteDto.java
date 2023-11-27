package com.github.balcon.venue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MusicianWriteDto(@NotBlank String name,
                               @NotBlank String role,
                               @NotNull Integer bandId) {
}
