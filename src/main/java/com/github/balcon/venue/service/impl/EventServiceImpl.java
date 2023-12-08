package com.github.balcon.venue.service.impl;

import static java.util.Objects.requireNonNullElse;

import com.github.balcon.venue.dto.EventReadDto;
import com.github.balcon.venue.dto.EventWriteDto;
import com.github.balcon.venue.dto.mapper.EventMapper;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.exception.ResourceNotFoundException;
import com.github.balcon.venue.repository.BandRepository;
import com.github.balcon.venue.repository.EventRepository;
import com.github.balcon.venue.service.EventService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for {@link com.github.balcon.venue.entity.Event}.
 *
 * @author Konstantin Balykov
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
  private final EventRepository repository;
  private final BandRepository bandRepository;
  private final EventMapper mapper;

  @Override
  public EventReadDto findById(int id) {
    return repository.findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new ResourceNotFoundException("Event", id));
  }

  @Override
  public List<EventReadDto> findAll() {
    return repository.findAll().stream()
            .map(mapper::toDto)
            .toList();
  }

  @Override
  public List<EventReadDto> findByDate(LocalDate date) {
    return repository.findByDate(date).stream()
            .map(mapper::toDto)
            .toList();
  }

  @Override
  @Transactional
  public EventReadDto save(EventWriteDto writeDto) {
    return mapper.toDto(repository.save(mapper.toEntity(writeDto)));
  }

  @Override
  @Transactional
  public void update(int id, EventWriteDto dto) {
    Event event = repository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Event", id));
    event.setName(requireNonNullElse(dto.name(), event.getName()));
    event.setDateTime(requireNonNullElse(dto.dateTime(), event.getDateTime()));
    repository.save(event);
  }

  @Override
  @Transactional
  public void delete(int id) {
    repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Event", id));
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void addBand(int eventId, int bandId) {
    Event event = repository.findById(eventId).orElseThrow(() ->
            new ResourceNotFoundException("Event", eventId));
    Band band = bandRepository.findById(bandId).orElseThrow(() ->
            new ResourceNotFoundException("Band", bandId));
    event.addBand(band);
    repository.save(event);
  }

  @Override
  @Transactional
  public void removeBand(int eventId, int bandId) {
    Event event = repository.findById(eventId).orElseThrow(() ->
            new ResourceNotFoundException("Event", eventId));
    Band band = bandRepository.findById(bandId).orElseThrow(() ->
            new ResourceNotFoundException("Band", bandId));
    event.removeBand(band);
    repository.save(event);
  }
}
