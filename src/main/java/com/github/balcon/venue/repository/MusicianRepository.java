package com.github.balcon.venue.repository;

import com.github.balcon.venue.entity.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicianRepository extends JpaRepository<Musician, Integer> {
    List<Musician> findByNameContainsIgnoreCase(String name);
}
