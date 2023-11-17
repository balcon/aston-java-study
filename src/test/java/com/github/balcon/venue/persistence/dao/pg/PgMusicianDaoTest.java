package com.github.balcon.venue.persistence.dao.pg;

import com.github.balcon.venue.persistence.AbstractMusicianPersistenceTest;

class PgMusicianDaoTest extends AbstractMusicianPersistenceTest {

    public PgMusicianDaoTest() {
        super(new PgDaoFactory().getMusicianPersistence());
    }
}
