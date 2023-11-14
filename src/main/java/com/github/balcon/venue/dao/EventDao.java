package com.github.balcon.venue.dao;

import com.github.balcon.venue.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventDao extends CrudDao<Event> {
    List<Event> findByDate(LocalDate date);
}
