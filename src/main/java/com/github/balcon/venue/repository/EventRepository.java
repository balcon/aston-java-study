package com.github.balcon.venue.repository;

import static com.github.balcon.venue.entity.Event.INCLUDE_BANDS;

import com.github.balcon.venue.entity.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * JPA Repository for {@link Event}.
 *
 * @author Konstantin Balykov
 */

@SuppressWarnings("NullableProblems")
public interface EventRepository extends JpaRepository<Event, Integer> {
  @EntityGraph(INCLUDE_BANDS)
  Optional<Event> findById(Integer id);

  @EntityGraph(INCLUDE_BANDS)
  List<Event> findAll();

  @Query("SELECT e FROM Event e LEFT JOIN FETCH e.bands WHERE DATE(e.dateTime) = :date")
  List<Event> findByDate(@Param("date") LocalDate date);
}
