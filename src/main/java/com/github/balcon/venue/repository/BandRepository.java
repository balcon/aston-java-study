package com.github.balcon.venue.repository;

import com.github.balcon.venue.entity.Band;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BandRepository extends JpaRepository<Band, Integer> {
}
