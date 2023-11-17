package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.persistence.AbstractMusicianPersistenceTest;

class MusicianRepositoryTest extends AbstractMusicianPersistenceTest {

    public MusicianRepositoryTest() {
        super(new RepositoryFactory().getMusicianPersistence());
    }
}
