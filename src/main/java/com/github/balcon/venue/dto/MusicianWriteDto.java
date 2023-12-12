package com.github.balcon.venue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

/**
 * DTO passed to {@link com.github.balcon.venue.service.MusicianService}.
 *
 * @author Konstantin Balykov
 */

@Builder
public record MusicianWriteDto(@NotBlank String name,
                               @NotBlank String role,
                               @NotNull Integer bandId) {
}
