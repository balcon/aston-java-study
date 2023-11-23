package com.github.balcon.venue.repository;

import com.github.balcon.venue.entity.Band;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static com.github.balcon.venue.entity.Band.INCLUDE_MUSICIANS;

@SuppressWarnings("NullableProblems")
public interface BandRepository extends JpaRepository<Band, Integer> {
    @EntityGraph(INCLUDE_MUSICIANS)
    Optional<Band> findById(Integer id);

    @EntityGraph(INCLUDE_MUSICIANS)
    List<Band> findAll();
}
