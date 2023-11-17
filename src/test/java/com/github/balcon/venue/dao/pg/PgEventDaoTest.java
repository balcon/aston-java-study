package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.entity.Event;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PgEventDaoTest extends BaseDaoTest {
    private final EventDao eventDao = new PgDaoFactory().getEventDao();

    @Test
    void save() {
        Event newEvent = Event.builder()
                .name("New event")
                .dateTime(LocalDateTime.of(2024, 1, 1, 12, 0)).build();

        assertThat(eventDao.save(newEvent).getId()).isNotNull();
        assertThat(eventDao.findAll()).hasSize(4);
    }

    @Test
    void findById() {
        Optional<Event> event = eventDao.find(102);

        assertThat(event).isPresent();
        assertThat(event.get().getName()).isEqualTo("Third Event");
        assertThat(event.get().getBands()).hasSize(2);
    }

    @Test
    void findAll() {
        List<Event> events = eventDao.findAll();

        assertThat(events).hasSize(3);
        assertThat(events.get(1).getBands()).isNotEmpty();
    }

    @Test
    void findByDate() {
        LocalDate date = LocalDate.of(2023, 12, 31);
        List<Event> events = eventDao.findByDate(date);

        assertThat(events).hasSize(2);
        assertThat(events.get(0).getDateTime().toLocalDate()).isEqualTo(date);
        assertThat(events.get(0).getBands()).isNotEmpty();
    }

    @Test
    void update() {
        int eventId = 100;
        String newName = "New Name";
        Event event = eventDao.find(eventId).orElseThrow();
        event.setName(newName);

        assertThat(eventDao.update(event)).isTrue();
        assertThat(eventDao.find(eventId).orElseThrow().getName()).isEqualTo(newName);
    }

    @Test
    void updateNullId() {
        Event event = eventDao.find(100).orElseThrow();
        event.setId(null);

        assertThat(eventDao.update(event)).isFalse();
    }

    @Test
    void delete() {
        int eventId = 100;

        assertThat(eventDao.delete(eventId)).isTrue();
        assertThat(eventDao.findAll()).hasSize(2);
        assertThat(eventDao.find(eventId)).isNotPresent();
    }

    @Test
    void addBand() {
        int eventId = 100;
        eventDao.addBand(eventId, 103);

        assertThat(eventDao.find(eventId).orElseThrow().getBands()).hasSize(4);
    }

    @Test
    void removeBand() {
        int eventId = 100;
        eventDao.removeBand(eventId, 100);

        assertThat(eventDao.find(eventId).orElseThrow().getBands()).hasSize(2);
    }
}
