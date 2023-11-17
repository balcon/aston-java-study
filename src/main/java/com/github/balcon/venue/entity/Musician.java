package com.github.balcon.venue.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public class Musician extends AbstractNamedEntity {
    private String role;
    private Band band;
}
