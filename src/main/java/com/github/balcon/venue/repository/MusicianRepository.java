package com.github.balcon.venue.repository;

import com.github.balcon.venue.entity.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicianRepository extends JpaRepository<Musician, Integer> {
}
