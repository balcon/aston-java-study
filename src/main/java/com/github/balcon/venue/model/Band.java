package com.github.balcon.venue.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Band {
    private Integer id;
    private String name;
    private List<Musician> musicians = new ArrayList<>();

    public void addMusician(Musician musician) {
        musicians.add(musician);
    }

    public boolean removeMusician(Musician musician) {
        return musicians.remove(musician);
    }
}
