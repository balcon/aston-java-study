package com.github.balcon.venue.persistence.dao.pg;

import com.github.balcon.venue.persistence.AbstractBandPersistenceTest;

class PgBandDaoTest extends AbstractBandPersistenceTest {

    public PgBandDaoTest() {
        super(new PgDaoFactory().getBandPersistence());
    }
}
