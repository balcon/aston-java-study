package com.github.balcon.venue.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@Getter
@Setter
public class Event extends AbstractNamedEntity {
    private LocalDateTime dateTime;
    @Builder.Default
    private List<Band> bands = new ArrayList<>();

    public void addBand(Band band) {
        bands.add(band);
    }
}
