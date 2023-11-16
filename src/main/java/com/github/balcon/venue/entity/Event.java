package com.github.balcon.venue.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Event {
    private Integer id;
    private String name;
    private LocalDateTime dateTime;
    @Builder.Default
    private List<Band> bands = new ArrayList<>();

    public void addBand(Band band){
        bands.add(band);
    }
}
