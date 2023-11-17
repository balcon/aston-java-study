package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.persistence.BandPersistence;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.persistence.dao.pg.PgDaoFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PgBandDaoTest extends BaseDaoTest {
    private final BandPersistence bandPersistence = new PgDaoFactory().getBandPersistence();

    @Test
    void save() {
        Band newBand = Band.builder()
                .name("New Band").build();

        assertThat(bandPersistence.save(newBand).getId()).isNotNull();
        assertThat(bandPersistence.findAll()).hasSize(5);
    }

    @Test
    void findById() {
        Optional<Band> band = bandPersistence.find(100);

        assertThat(band).isPresent();
        assertThat(band.get().getName()).isEqualTo("Band Number One");
        assertThat(band.get().getMusicians()).hasSize(4);
    }

    @Test
    void findByIdWithoutMusicians() {
        Optional<Band> band = bandPersistence.find(103);

        assertThat(band).isPresent();
        assertThat(band.get().getName()).isEqualTo("Fourth Band");
        assertThat(band.get().getMusicians()).isEmpty();
    }

    @Test
    void findAll() {
        List<Band> bands = bandPersistence.findAll();

        assertThat(bands).hasSize(4);
        assertThat(bands.get(0).getMusicians()).isNotEmpty();
    }

    @Test
    void update() {
        int bandId = 100;
        String newName = "New Band Name";
        Band band = bandPersistence.find(bandId).orElseThrow();
        band.setName(newName);

        assertThat(bandPersistence.update(band)).isTrue();
        assertThat(bandPersistence.find(bandId).orElseThrow().getName()).isEqualTo(newName);
    }

    @Test
    void updateNullId() {
        Band band = bandPersistence.find(100).orElseThrow();
        band.setId(null);

        assertThat(bandPersistence.update(band)).isFalse();
    }

    @Test
    void delete() {
        int bandId = 100;

        assertThat(bandPersistence.delete(bandId)).isTrue();
        assertThat(bandPersistence.findAll()).hasSize(3);
        assertThat(bandPersistence.find(bandId)).isNotPresent();
    }
}
