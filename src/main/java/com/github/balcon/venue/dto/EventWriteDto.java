package com.github.balcon.venue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;

/**
 * DTO passed to {@link com.github.balcon.venue.service.EventService}.
 *
 * @author Konstantin Balykov
 */

@Builder
public record EventWriteDto(@NotBlank String name,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
                            @NotNull LocalDateTime dateTime) {
}
