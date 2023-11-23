package com.github.balcon.venue.dto;

import lombok.Builder;

@Builder
public record BandDto(int id,
                      String name) {
}
