package com.github.balcon.venue.service;

import com.github.balcon.venue.dto.MusicianReadDto;
import com.github.balcon.venue.dto.MusicianWriteDto;

import java.util.List;

public interface MusicianService extends AbstractService<MusicianReadDto, MusicianWriteDto> {
    List<MusicianReadDto> findByName(String name);
}
