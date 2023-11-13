package com.github.balcon.venue.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Event {
    private Integer id;
    private String name;
    private LocalDateTime dateTime;
    private List<Band> bands = new ArrayList<>();

    public void addBand(Band band) {
        bands.add(band);
    }

    public boolean removeBand(Band band) {
        return bands.remove(band);
    }
}
