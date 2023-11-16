package com.github.balcon.venue.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Band {
    private Integer id;
    private String name;
    @Builder.Default
    private List<Musician> musicians = new ArrayList<>();

    public void addMusician(Musician musician) {
        musicians.add(musician);
    }
}
