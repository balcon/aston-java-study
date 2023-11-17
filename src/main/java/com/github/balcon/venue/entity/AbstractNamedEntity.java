package com.github.balcon.venue.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
public abstract class AbstractNamedEntity {
    private Integer id;
    private String name;
}
