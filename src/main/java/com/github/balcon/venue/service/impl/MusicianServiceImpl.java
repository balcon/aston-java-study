package com.github.balcon.venue.service.impl;

import com.github.balcon.venue.dto.MusicianReadDto;
import com.github.balcon.venue.dto.MusicianWriteDto;
import com.github.balcon.venue.dto.mapper.MusicianMapper;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.exception.ResourceNotFoundException;
import com.github.balcon.venue.repository.BandRepository;
import com.github.balcon.venue.repository.MusicianRepository;
import com.github.balcon.venue.service.MusicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MusicianServiceImpl implements MusicianService {
    private final MusicianRepository repository;
    private final BandRepository bandRepository;
    private final MusicianMapper mapper;

    @Override
    public MusicianReadDto findById(int id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Musician", id));
    }

    @Override
    public List<MusicianReadDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<MusicianReadDto> findByName(String name) {
        return repository.findByNameContainsIgnoreCase(name).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public MusicianReadDto save(MusicianWriteDto dto) {
        Band band = bandRepository.findById(dto.bandId())
                .orElseThrow(() -> new ResourceNotFoundException("Band", dto.bandId()));
        Musician musician = mapper.toEntity(dto);
        musician.setBand(band);
        return mapper.toDto(repository.save(musician));
    }

    @Override
    @Transactional
    public void update(int id, MusicianWriteDto dto) {
        Musician musician = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Musician", id));
        musician.setName(requireNonNullElse(dto.name(), musician.getName()));
        musician.setRole(requireNonNullElse(dto.role(), musician.getRole()));
        if (dto.bandId() != null) {
            musician.setBand(bandRepository.findById(dto.bandId())
                    .orElseThrow(() -> new ResourceNotFoundException("Band", dto.bandId())));
        }
        repository.save(musician);
    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Musician", id));
        repository.deleteById(id);
    }
}
