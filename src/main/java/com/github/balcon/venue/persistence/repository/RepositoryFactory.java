package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.persistence.BandPersistence;
import com.github.balcon.venue.persistence.EventPersistence;
import com.github.balcon.venue.persistence.MusicianPersistence;
import com.github.balcon.venue.persistence.PersistenceFactory;

public class RepositoryFactory implements PersistenceFactory {
    @Override
    public BandPersistence getBandPersistence() {
        return null;
    }

    @Override
    public EventPersistence getEventPersistence() {
        return null;
    }

    @Override
    public MusicianPersistence getMusicianPersistence() {
        return null;
    }
}
