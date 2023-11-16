package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.BandDao;
import com.github.balcon.venue.entity.Band;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PgBandDaoTest extends BaseDaoTest {
    private final BandDao bandDao = new PgDaoFactory().getBandDao();

    @Test
    void save() {
        Band newBand = Band.builder()
                .name("New Band").build();

        assertThat(bandDao.save(newBand).getId()).isNotNull();
        assertThat(bandDao.findAll()).hasSize(5);
    }

    @Test
    void findById() {
        Optional<Band> band = bandDao.find(100);

        assertThat(band).isPresent();
        assertThat(band.get().getName()).isEqualTo("Band Number One");
        assertThat(band.get().getMusicians()).hasSize(4);
    }

    @Test
    void findByIdWithoutMusicians() {
        Optional<Band> band = bandDao.find(103);

        assertThat(band).isPresent();
        assertThat(band.get().getName()).isEqualTo("Fourth Band");
        assertThat(band.get().getMusicians()).isEmpty();
    }

    @Test
    void findAll() {
        List<Band> bands = bandDao.findAll();

        assertThat(bands).hasSize(4);
        assertThat(bands.get(0).getMusicians()).isNotEmpty();
    }

    @Test
    void update() {
        int bandId = 100;
        String newName = "New Band Name";
        Band band = bandDao.find(bandId).orElseThrow();
        band.setName(newName);

        assertThat(bandDao.update(band)).isTrue();
        assertThat(bandDao.find(bandId).orElseThrow().getName()).isEqualTo(newName);
    }

    @Test
    void updateNullId() {
        Band band = bandDao.find(100).orElseThrow();
        band.setId(null);

        assertThat(bandDao.update(band)).isFalse();
    }

    @Test
    void delete() {
        int bandId = 100;

        assertThat(bandDao.delete(bandId)).isTrue();
        assertThat(bandDao.findAll()).hasSize(3);
        assertThat(bandDao.find(bandId)).isNotPresent();
    }
}
