package com.github.balcon.venue.dao;

import com.github.balcon.venue.entity.Musician;

import java.util.List;

public interface MusicianDao extends CrudDao<Musician> {
    List<Musician> findByName(String name);
}
