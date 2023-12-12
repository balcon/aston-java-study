package com.github.balcon.venue.repository;

import static com.github.balcon.venue.entity.Musician.INCLUDE_BAND;

import com.github.balcon.venue.entity.Musician;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for {@link Musician}.
 *
 * @author Konstantin Balykov
 */

@SuppressWarnings("NullableProblems")
public interface MusicianRepository extends JpaRepository<Musician, Integer> {
  @EntityGraph(INCLUDE_BAND)
  Optional<Musician> findById(Integer id);

  @EntityGraph(INCLUDE_BAND)
  List<Musician> findAll();

  @EntityGraph(INCLUDE_BAND)
  List<Musician> findByNameContainsIgnoreCase(String name);
}
