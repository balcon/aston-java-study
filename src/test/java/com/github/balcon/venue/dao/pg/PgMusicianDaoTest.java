package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.persistence.MusicianPersistence;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.persistence.dao.pg.PgDaoFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PgMusicianDaoTest extends BaseDaoTest {
    private final MusicianPersistence musicianPersistence = new PgDaoFactory().getMusicianPersistence();

    @Test
    void save() {
        Musician newMusician = Musician.builder()
                .name("Mr. Useless")
                .role("Manager")
                .band(Band.builder().id(100).build()).build();

        assertThat(musicianPersistence.save(newMusician).getId()).isNotNull();
        assertThat(musicianPersistence.findAll()).hasSize(9);
    }

    @Test
    void findById() {
        Optional<Musician> musician = musicianPersistence.find(100);

        assertThat(musician).isPresent();
        assertThat(musician.get().getName()).isEqualTo("Musician #100");
        assertThat(musician.get().getBand().getName()).isEqualTo("Band Number One");
    }

    @Test
    void findAll() {
        List<Musician> musicians = musicianPersistence.findAll();

        assertThat(musicians).hasSize(8);
        assertThat(musicians.get(0).getBand()).isNotNull();
    }

    @Test
    void findByName() {
        List<Musician> johns = musicianPersistence.findByName("John");

        assertThat(johns).hasSize(3);
        assertThat(johns.get(0).getBand()).isNotNull();
    }

    @Test
    void update() {
        int musicianId = 100;
        String newName = "New Name";
        int newBandId = 101;
        Musician musician = musicianPersistence.find(musicianId).orElseThrow();
        musician.setName(newName);
        musician.setBand(Band.builder().id(newBandId).build());

        assertThat(musicianPersistence.update(musician)).isTrue();
        assertThat(musicianPersistence.find(musicianId).orElseThrow().getName()).isEqualTo(newName);
        assertThat(musicianPersistence.find(musicianId).orElseThrow().getBand().getId()).isEqualTo(newBandId);
    }

    @Test
    void updateNullId() {
        Musician musician = musicianPersistence.find(100).orElseThrow();
        musician.setId(null);

        assertThat(musicianPersistence.update(musician)).isFalse();
    }

    @Test
    void delete() {
        int musicianId = 100;

        assertThat(musicianPersistence.delete(musicianId)).isTrue();
        assertThat(musicianPersistence.findAll()).hasSize(7);
        assertThat(musicianPersistence.find(musicianId)).isNotPresent();
    }
}
