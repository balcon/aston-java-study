package com.github.balcon.venue.service.impl;

import com.github.balcon.venue.dto.EventReadDto;
import com.github.balcon.venue.dto.EventWriteDto;
import com.github.balcon.venue.dto.mapper.EventMapper;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.repository.BandRepository;
import com.github.balcon.venue.repository.EventRepository;
import com.github.balcon.venue.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.requireNonNullElse;

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
                .orElseThrow();
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
        Event event = repository.findById(id).orElseThrow();
        event.setName(requireNonNullElse(dto.name(), event.getName()));
        event.setDateTime(requireNonNullElse(dto.dateTime(), event.getDateTime()));
        repository.save(event);
    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.findById(id).orElseThrow();
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void addBand(int serviceId, int bandId) {
        Event event = repository.findById(serviceId).orElseThrow();
        Band band = bandRepository.findById(bandId).orElseThrow();
        event.addBand(band);
        repository.save(event);
    }

    @Override
    @Transactional
    public void removeBand(int serviceId, int bandId) {
        Event event = repository.findById(serviceId).orElseThrow();
        Band band = bandRepository.findById(bandId).orElseThrow();
        event.removeBand(band);
        repository.save(event);
    }
}
