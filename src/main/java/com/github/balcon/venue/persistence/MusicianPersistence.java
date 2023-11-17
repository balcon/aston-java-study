package com.github.balcon.venue.persistence;

import com.github.balcon.venue.entity.Musician;

import java.util.List;

public interface MusicianPersistence extends Crud<Musician> {
    List<Musician> findByName(String name);
}
