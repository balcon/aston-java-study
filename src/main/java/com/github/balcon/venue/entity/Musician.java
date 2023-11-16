package com.github.balcon.venue.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
public class Musician {
    private Integer id;
    private String name;
    private String role;
    @ToString.Exclude
    private Band band;
}
