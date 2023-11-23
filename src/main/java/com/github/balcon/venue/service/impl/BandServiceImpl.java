package com.github.balcon.venue.service.impl;

import com.github.balcon.venue.dto.BandReadDto;
import com.github.balcon.venue.dto.BandWriteDto;
import com.github.balcon.venue.dto.mapper.BandMapper;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.repository.BandRepository;
import com.github.balcon.venue.service.BandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.requireNonNullElse;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BandServiceImpl implements BandService {
    private final BandRepository repository;
    private final BandMapper mapper;

    @Override
    public BandReadDto findById(int id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow();
    }

    @Override
    public List<BandReadDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public BandReadDto save(BandWriteDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Override
    @Transactional
    public void update(int id, BandWriteDto dto) {
        Band band = repository.findById(id).orElseThrow();
        band.setName(requireNonNullElse(dto.name(), band.getName()));
        repository.save(band);
    }

    @Override
    @Transactional
    public void delete(int id) {
        repository.findById(id).orElseThrow();
        repository.deleteById(id);
    }
}
