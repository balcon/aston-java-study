package com.github.balcon.venue.service;

import com.github.balcon.venue.dto.EventReadDto;
import com.github.balcon.venue.dto.EventWriteDto;

import java.time.LocalDate;
import java.util.List;

public interface EventService extends AbstractService<EventReadDto, EventWriteDto> {
    List<EventReadDto> findByDate(LocalDate date);

    void addBand(int eventId, int bandId);

    void removeBand(int eventId, int bandId);
}
