package com.github.balcon.venue.dao;

import com.github.balcon.venue.model.Musician;

import java.util.Optional;

public interface MusicianDao extends CrudDao<Musician> {
    Optional<Musician> findByName(String name);
}