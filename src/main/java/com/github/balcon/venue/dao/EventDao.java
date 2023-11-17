package com.github.balcon.venue.dao;

import com.github.balcon.venue.entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventDao extends CrudDao<Event> {
    List<Event> findByDate(LocalDate date);

    boolean addBand(int eventId, int bandId);

    boolean removeBand(int eventId, int bandId);
}
