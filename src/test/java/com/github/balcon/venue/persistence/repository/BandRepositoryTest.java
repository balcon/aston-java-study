package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.persistence.AbstractBandPersistenceTest;

public class BandRepositoryTest extends AbstractBandPersistenceTest {

    public BandRepositoryTest() {
        super(new RepositoryFactory().getBandPersistence());
    }
}
