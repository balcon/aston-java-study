package com.github.balcon.venue.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Event {
    private Integer id;
    private String name;
    private LocalDateTime dateTime;
}
