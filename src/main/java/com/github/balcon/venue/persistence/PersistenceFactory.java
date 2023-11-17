package com.github.balcon.venue.persistence;

public interface PersistenceFactory {
    BandPersistence getBandPersistence();

    EventPersistence getEventPersistence();

    MusicianPersistence getMusicianPersistence();
}
