package com.github.balcon.venue.service;

import com.github.balcon.venue.BaseTest;
import com.github.balcon.venue.dto.BandReadDto;
import com.github.balcon.venue.dto.BandWriteDto;
import com.github.balcon.venue.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.balcon.venue.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
class BandServiceTest extends BaseTest implements ServiceTestMethods {
    private final BandService service;

    @Override
    @Test
    public void findById() {
        BandReadDto band = service.findById(BAND_ID);

        assertThat(band.name()).isEqualTo(BAND.name());
        assertThat(band.musicians()).hasSize(4);
    }

    @Override
    @Test
    public void findAll() {
        List<BandReadDto> bands = service.findAll();

        assertThat(bands).hasSize(4);
        assertThat(bands.get(0).musicians()).isNotNull();
    }

    @Override
    @Test
    public void findByIdNotExists() {
        assertThatThrownBy(() -> service.findById(DUMMY_ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Override
    @Test
    public void save() {
        BandWriteDto created = BandWriteDto.builder()
                .name("New band").build();
        BandReadDto band = service.save(created);

        assertThat(band.name()).isEqualTo(created.name());
        assertThat(service.findAll()).hasSize(4 + 1);
    }

    @Override
    @Test
    public void update() {
        int id = 100;
        BandWriteDto edited = BandWriteDto.builder()
                .name("New name").build();
        service.update(id, edited);
        BandReadDto band = service.findById(id);

        assertThat(band.name()).isEqualTo(edited.name());
    }

    @Override
    @Test
    public void updateNotExists() {
        BandWriteDto dummy = BandWriteDto.builder().build();

        assertThatThrownBy(() -> service.update(DUMMY_ID, dummy)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Override
    @Test
    public void delete() {
        service.delete(BAND_ID);

        assertThat(service.findAll()).hasSize(4 - 1);
        assertThatThrownBy(() -> service.findById(BAND_ID));
    }

    @Override
    @Test
    public void deleteNotExists() {
        assertThatThrownBy(() -> service.delete(DUMMY_ID)).isInstanceOf(ResourceNotFoundException.class);
    }
}
