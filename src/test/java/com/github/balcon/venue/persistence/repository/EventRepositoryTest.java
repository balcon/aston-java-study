package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.persistence.AbstractEventPersistenceTest;

class EventRepositoryTest extends AbstractEventPersistenceTest {

    public EventRepositoryTest() {
        super(new RepositoryFactory().getEventPersistence());
    }
}
