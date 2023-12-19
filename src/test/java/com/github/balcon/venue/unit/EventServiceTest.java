package com.github.balcon.venue.unit;

import com.github.balcon.venue.dto.mapper.EventMapper;
import com.github.balcon.venue.exception.ResourceNotFoundException;
import com.github.balcon.venue.repository.BandRepository;
import com.github.balcon.venue.repository.EventRepository;
import com.github.balcon.venue.service.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Optional;

import static com.github.balcon.venue.TestData.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventServiceTest extends BaseUnitTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private BandRepository bandRepository;
    @Spy
    private EventMapper mapper = Mappers.getMapper(EventMapper.class);
    @InjectMocks
    private EventServiceImpl service;

    @Test
    void findById_invokeRepositoryFindById() {
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(EVENT_DUMMY));

        service.findById(DUMMY_ID);

        verify(eventRepository).findById(anyInt());
    }

    @Test
    void findById_notExists_throwException() {
        when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(DUMMY_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_notExists_throwException() {
        when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(DUMMY_ID, EVENT_WRITE_DTO_DUMMY))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_notExists_throwException() {
        when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(DUMMY_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void addBand_invokeRepositoriesFindById() {
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(EVENT_DUMMY));
        when(bandRepository.findById(anyInt())).thenReturn(Optional.of(BAND_DUMMY));

        service.addBand(DUMMY_ID, DUMMY_ID);

        verify(eventRepository).findById(anyInt());
        verify(eventRepository).save(any());
        verify(bandRepository).findById(anyInt());
    }

    @Test
    void removeBand_invokeRepositoriesFindById() {
        when(eventRepository.findById(anyInt())).thenReturn(Optional.of(EVENT_DUMMY));
        when(bandRepository.findById(anyInt())).thenReturn(Optional.of(BAND_DUMMY));

        service.removeBand(DUMMY_ID, DUMMY_ID);

        verify(eventRepository).findById(anyInt());
        verify(eventRepository).save(any());
        verify(bandRepository).findById(anyInt());
    }
}
