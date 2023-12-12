package com.github.balcon.venue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

/**
 * DTO passed to {@link com.github.balcon.venue.service.BandService}.
 *
 * @author Konstantin Balykov
 */

@Builder
public record BandWriteDto(@NotBlank String name) {
}
