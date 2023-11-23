package com.github.balcon.venue.repository;

import com.github.balcon.venue.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e LEFT JOIN FETCH e.bands WHERE DATE(e.dateTime) = :date")
    List<Event> findByDate(@Param("date") LocalDate date);
}
