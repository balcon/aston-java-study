package com.github.balcon.venue.persistence.dao.pg;

import com.github.balcon.venue.persistence.AbstractEventPersistenceTest;

class PgEventDaoTest extends AbstractEventPersistenceTest {

    protected PgEventDaoTest() {
        super(new PgDaoFactory().getEventPersistence());
    }
}
