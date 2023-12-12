package com.github.balcon.venue.repository;

import static com.github.balcon.venue.entity.Band.INCLUDE_MUSICIANS;

import com.github.balcon.venue.entity.Band;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository for {@link Band}.
 *
 * @author Konstantin Balykov
 */

@SuppressWarnings("NullableProblems")
public interface BandRepository extends JpaRepository<Band, Integer> {
  @EntityGraph(INCLUDE_MUSICIANS)
  Optional<Band> findById(Integer id);

  @EntityGraph(INCLUDE_MUSICIANS)
  List<Band> findAll();
}
