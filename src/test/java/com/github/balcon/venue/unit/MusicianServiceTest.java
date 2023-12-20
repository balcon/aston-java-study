package com.github.balcon.venue.unit;

import com.github.balcon.venue.dto.mapper.MusicianMapper;
import com.github.balcon.venue.exception.ResourceNotFoundException;
import com.github.balcon.venue.repository.BandRepository;
import com.github.balcon.venue.repository.MusicianRepository;
import com.github.balcon.venue.service.impl.MusicianServiceImpl;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Collections;
import java.util.Optional;

import static com.github.balcon.venue.TestData.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MusicianServiceTest extends BaseUnitTest {
    @Mock
    private MusicianRepository musicianRepository;
    @Mock
    private BandRepository bandRepository;
    @Spy
    private MusicianMapper mapper = Mappers.getMapper(MusicianMapper.class);
    @InjectMocks
    private MusicianServiceImpl service;

    @Test
    void findById_invokeRepositoryFindById() {
        when(musicianRepository.findById(anyInt())).thenReturn(Optional.of(MUSICIAN_DUMMY));

        service.findById(DUMMY_ID);

        verify(musicianRepository).findById(anyInt());
    }

    @Test
    void findByName_invokeRepositoryFindByName() {
        when(musicianRepository.findByNameContainsIgnoreCase(anyString()))
                .thenReturn(Collections.emptyList());

        service.findByName("");

        verify(musicianRepository).findByNameContainsIgnoreCase(anyString());
    }

    @Test
    void findById_notExists_throwException() {
        when(musicianRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(DUMMY_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void save_bandNotExists_throwException() {
        when(bandRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.save(MUSICIAN_WRITE_DTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_notExists_throwException() {
        when(musicianRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(DUMMY_ID, MUSICIAN_WRITE_DTO_DUMMY))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_bandNotExists_throwException() {
        when(musicianRepository.findById(anyInt())).thenReturn(Optional.of(MUSICIAN_DUMMY));
        when(bandRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(DUMMY_ID, MUSICIAN_WRITE_DTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_notExists_throwException() {
        when(musicianRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(DUMMY_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
