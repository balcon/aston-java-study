package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.MusicianDao;
import com.github.balcon.venue.entity.Musician;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PgMusicianDaoTest extends BaseDaoTest {
    private final MusicianDao musicianDao = new PgDaoFactory().getMusicianDao();

    @Test
    void save() {
        Musician newMusician = Musician.builder()
                .name("Mr. Useless")
                .role("Manager")
                .bandId(100).build();

        assertThat(musicianDao.save(newMusician).getId()).isNotNull();
        assertThat(musicianDao.findAll()).hasSize(9);
    }

    @Test
    void findById() {
        Optional<Musician> musician = musicianDao.find(100);

        assertThat(musician).isPresent();
        assertThat(musician.orElseThrow().getName()).isEqualTo("Musician #100");
    }

    @Test
    void findAll() {
        List<Musician> musicians = musicianDao.findAll();

        assertThat(musicians).hasSize(8);
    }

    @Test
    void findByName() {
        List<Musician> johns = musicianDao.findByName("John");

        assertThat(johns).hasSize(3);
    }

    @Test
    void update() {
        int musicianId = 100;
        String newName = "New Name";
        int newBandId = 101;
        Musician musician = musicianDao.find(musicianId).orElseThrow();
        musician.setName(newName);
        musician.setBandId(newBandId);

        assertThat(musicianDao.update(musician)).isTrue();
        assertThat(musicianDao.find(musicianId).orElseThrow().getName()).isEqualTo(newName);
        assertThat(musicianDao.find(musicianId).orElseThrow().getBandId()).isEqualTo(newBandId);
    }

    @Test
    void updateNullId() {
        Musician musician = musicianDao.find(100).orElseThrow();
        musician.setId(null);

        assertThat(musicianDao.update(musician)).isFalse();
    }

    @Test
    void delete() {
        int musicianId = 100;

        assertThat(musicianDao.delete(musicianId)).isTrue();
        assertThat(musicianDao.findAll()).hasSize(7);
        assertThat(musicianDao.find(musicianId)).isNotPresent();
    }
}
