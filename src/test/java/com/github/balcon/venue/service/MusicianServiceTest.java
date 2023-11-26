package com.github.balcon.venue.service;

import com.github.balcon.venue.BaseTest;
import com.github.balcon.venue.TestData;
import com.github.balcon.venue.dto.MusicianReadDto;
import com.github.balcon.venue.dto.MusicianWriteDto;
import com.github.balcon.venue.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.balcon.venue.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
class MusicianServiceTest extends BaseTest implements ServiceTestMethods {
    private final MusicianService service;

    @Override
    @Test
    public void findById() {
        MusicianReadDto musicianDto = service.findById(TestData.MUSICIAN_ID);

        assertThat(musicianDto).isEqualTo(MusicianReadDto.builder()
                .id(120)
                .name("Musician #120")
                .role("Singer")
                .band(BAND).build());
    }

    @Override
    @Test
    public void findAll() {
        List<MusicianReadDto> musicians = service.findAll();

        assertThat(musicians).hasSize(8);
        assertThat(musicians.get(0).band()).isNotNull();
    }

    @Test
    void findByName() {
        String name = "john";
        List<MusicianReadDto> musicians = service.findByName(name);

        assertThat(musicians).hasSize(3);
        assertThat(musicians.get(0).name()).containsIgnoringCase(name);
    }

    @Override
    @Test
    public void findByIdNotExists() {
        assertThatThrownBy(() -> service.findById(DUMMY_ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Override
    @Test
    public void save() {
        MusicianWriteDto created = MusicianWriteDto.builder()
                .name("Name")
                .role("Role")
                .bandId(BAND_ID).build();
        MusicianReadDto musician = service.save(created);

        assertThat(service.findAll()).hasSize(8 + 1);
        assertThat(musician.name()).isEqualTo(created.name());
        assertThat(musician.band()).isEqualTo(BAND);
    }

    @Override
    @Test
    public void update() {
        int id = 120;
        MusicianWriteDto edited = MusicianWriteDto.builder()
                .name("New name")
                .role("New role").build();
        service.update(id, edited);
        MusicianReadDto musician = service.findById(id);

        assertThat(musician.name()).isEqualTo(edited.name());
        assertThat(musician.role()).isEqualTo(edited.role());
        assertThat(musician.band()).isEqualTo(BAND);
    }

    @Override
    @Test
    public void updateNotExists() {
        MusicianWriteDto dummy = MusicianWriteDto.builder().build();

        assertThatThrownBy(() -> service.update(DUMMY_ID, dummy)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Override
    @Test
    public void delete() {
        service.delete(MUSICIAN_ID);

        assertThat(service.findAll()).hasSize(8 - 1);
        assertThatThrownBy(() -> service.findById(MUSICIAN_ID));
    }

    @Override
    @Test
    public void deleteNotExists() {
        assertThatThrownBy(() -> service.delete(DUMMY_ID)).isInstanceOf(ResourceNotFoundException.class);
    }
}
