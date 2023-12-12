package com.github.balcon.venue.service;

import com.github.balcon.venue.BaseTest;
import com.github.balcon.venue.dto.EventReadDto;
import com.github.balcon.venue.dto.EventWriteDto;
import com.github.balcon.venue.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.github.balcon.venue.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RequiredArgsConstructor
class EventServiceTest extends BaseTest implements ServiceTestMethods {
    private final EventService service;

    @Override
    @Test
    public void findById() {
        EventReadDto event = service.findById(EVENT_ID);

        assertThat(event.name()).isEqualTo("First Event");
        assertThat(event.bands()).hasSize(3);
    }

    @Override
    @Test
    public void findAll() {
        List<EventReadDto> events = service.findAll();

        assertThat(events).hasSize(3);
        assertThat(events.get(0).bands()).isNotNull();
    }

    @Test
    public void findByDate() {
        LocalDate date = LocalDate.of(2023, 12, 31);
        List<EventReadDto> events = service.findByDate(date);

        assertThat(events).hasSize(2);
        assertThat(events.get(0).dateTime().toLocalDate()).isEqualTo(date);
        assertThat(events.get(0).bands()).isNotNull();
    }

    @Override
    @Test
    public void findByIdNotExists() {
        assertThatThrownBy(() -> service.findById(DUMMY_ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Override
    @Test
    public void save() {
        EventWriteDto created = EventWriteDto.builder()
                .name("New Event")
                .dateTime(LocalDateTime.now()).build();
        EventReadDto event = service.save(created);

        assertThat(event.name()).isEqualTo(created.name());
        assertThat(service.findAll()).hasSize(3 + 1);
    }

    @Override
    @Test
    public void update() {
        EventWriteDto edited = EventWriteDto.builder()
                .name("New Name").build();
        service.update(EVENT_ID, edited);
        EventReadDto event = service.findById(EVENT_ID);

        assertThat(event.name()).isEqualTo(edited.name());
        assertThat(event.dateTime()).isNotNull();
    }

    @Override
    @Test
    public void updateNotExists() {
        EventWriteDto dummy = EventWriteDto.builder().build();

        assertThatThrownBy(() -> service.update(DUMMY_ID, dummy)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Override
    @Test
    public void delete() {
        service.delete(EVENT_ID);

        assertThat(service.findAll()).hasSize(3 - 1);
        assertThatThrownBy(() -> service.findById(EVENT_ID));
    }

    @Override
    @Test
    public void deleteNotExists() {
        assertThatThrownBy(() -> service.delete(DUMMY_ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void addBand() {
        int bandId = 103;
        service.addBand(EVENT_ID, bandId);
        EventReadDto event = service.findById(EVENT_ID);

        assertThat(event.bands()).hasSize(3 + 1);
        assertThat(event.bands().stream()
                .filter(b -> b.id() == bandId)
                .toList()).isNotEmpty();
    }

    @Test
    void addBandNotExists() {
        assertThatThrownBy(() -> service.addBand(EVENT_ID, DUMMY_ID)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void removeBand() {
        service.removeBand(EVENT_ID, BAND_ID);
        EventReadDto event = service.findById(EVENT_ID);

        assertThat(event.bands()).hasSize(3 - 1);
        assertThat(event.bands().stream()
                .filter(b -> b.id() == BAND_ID)
                .toList()).isEmpty();
    }

    @Test
    void removeBandNotExists() {
        assertThatThrownBy(() -> service.removeBand(EVENT_ID, DUMMY_ID)).isInstanceOf(ResourceNotFoundException.class);
    }
}
