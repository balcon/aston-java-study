package com.github.balcon.venue.service.impl;

import com.github.balcon.venue.dto.MusicianDto;
import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.service.MusicianService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicianServiceImpl implements MusicianService {
    @Override
    public MusicianDto findById(int id) {
        return null;
    }

    @Override
    public List<MusicianDto> findAll() {
        return null;
    }

    @Override
    public MusicianDto save(Musician entity) {
        return null;
    }

    @Override
    public void update(int id, Musician entity) {

    }

    @Override
    public void delete(int id) {

    }
}
