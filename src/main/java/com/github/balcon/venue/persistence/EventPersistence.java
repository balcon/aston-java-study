package com.github.balcon.venue.persistence;

import com.github.balcon.venue.entity.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventPersistence extends Crud<Event> {
    List<Event> findByDate(LocalDate date);

    boolean addBand(int eventId, int bandId);

    boolean removeBand(int eventId, int bandId);
}
