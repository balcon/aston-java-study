package com.github.balcon.venue.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Musician {
    private Integer id;
    private String name;
    private String role;
    private int bandId;
}
