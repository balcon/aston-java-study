package com.github.balcon.venue.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Band {
    private Integer id;
    private String name;
}
