package com.github.balcon.venue.unit;

import com.github.balcon.venue.dto.mapper.BandMapper;
import com.github.balcon.venue.exception.ResourceNotFoundException;
import com.github.balcon.venue.repository.BandRepository;
import com.github.balcon.venue.service.impl.BandServiceImpl;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.*;

import java.util.Optional;

import static com.github.balcon.venue.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BandServiceTest extends BaseUnitTest {
    @Mock
    private BandRepository repository;
    @Spy
    private BandMapper mapper = Mappers.getMapper(BandMapper.class);
    @Captor
    private ArgumentCaptor<Integer> captor;
    @InjectMocks
    private BandServiceImpl service;

    @Test
    void findById_invokeRepositoryFindById() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(BAND_DUMMY));

        service.findById(DUMMY_ID);

        verify(repository).findById(captor.capture());
        assertThat(captor.getValue()).isEqualTo(DUMMY_ID);
    }

    @Test
    void findById_notExists_throwException() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(DUMMY_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_notExists_throwException() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.update(DUMMY_ID, BAND_WRITE_DTO_DUMMY))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void delete_notExists_throwException() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.delete(DUMMY_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
