package com.github.balcon.venue.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EventWriteDto(@NotBlank String name,
                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
                            @NotNull LocalDateTime dateTime) {
}
