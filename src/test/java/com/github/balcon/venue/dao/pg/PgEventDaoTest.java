package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.persistence.EventPersistence;
import com.github.balcon.venue.persistence.dao.pg.PgDaoFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PgEventDaoTest extends BaseDaoTest {
    private final EventPersistence eventPersistence = new PgDaoFactory().getEventPersistence();

    @Test
    void save() {
        Event newEvent = Event.builder()
                .name("New event")
                .dateTime(LocalDateTime.of(2024, 1, 1, 12, 0)).build();

        assertThat(eventPersistence.save(newEvent).getId()).isNotNull();
        assertThat(eventPersistence.findAll()).hasSize(4);
    }

    @Test
    void findById() {
        Optional<Event> event = eventPersistence.find(102);

        assertThat(event).isPresent();
        assertThat(event.get().getName()).isEqualTo("Third Event");
        assertThat(event.get().getBands()).hasSize(2);
    }

    @Test
    void findAll() {
        List<Event> events = eventPersistence.findAll();

        assertThat(events).hasSize(3);
        assertThat(events.get(1).getBands()).isNotEmpty();
    }

    @Test
    void findByDate() {
        LocalDate date = LocalDate.of(2023, 12, 31);
        List<Event> events = eventPersistence.findByDate(date);

        assertThat(events).hasSize(2);
        assertThat(events.get(0).getDateTime().toLocalDate()).isEqualTo(date);
        assertThat(events.get(0).getBands()).isNotEmpty();
    }

    @Test
    void update() {
        int eventId = 100;
        String newName = "New Name";
        Event event = eventPersistence.find(eventId).orElseThrow();
        event.setName(newName);

        assertThat(eventPersistence.update(event)).isTrue();
        assertThat(eventPersistence.find(eventId).orElseThrow().getName()).isEqualTo(newName);
    }

    @Test
    void updateNullId() {
        Event event = eventPersistence.find(100).orElseThrow();
        event.setId(null);

        assertThat(eventPersistence.update(event)).isFalse();
    }

    @Test
    void delete() {
        int eventId = 100;

        assertThat(eventPersistence.delete(eventId)).isTrue();
        assertThat(eventPersistence.findAll()).hasSize(2);
        assertThat(eventPersistence.find(eventId)).isNotPresent();
    }

    @Test
    void addBand() {
        int eventId = 100;
        eventPersistence.addBand(eventId, 103);

        assertThat(eventPersistence.find(eventId).orElseThrow().getBands()).hasSize(4);
    }

    @Test
    void removeBand() {
        int eventId = 100;
        eventPersistence.removeBand(eventId, 100);

        assertThat(eventPersistence.find(eventId).orElseThrow().getBands()).hasSize(2);
    }
}
