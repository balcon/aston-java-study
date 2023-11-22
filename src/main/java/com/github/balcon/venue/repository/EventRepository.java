package com.github.balcon.venue.repository;

import com.github.balcon.venue.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
