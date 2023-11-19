package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.persistence.BandPersistence;
import com.github.balcon.venue.persistence.EventPersistence;
import com.github.balcon.venue.persistence.MusicianPersistence;
import com.github.balcon.venue.persistence.PersistenceFactory;

public class RepositoryFactory implements PersistenceFactory {
    private static final MusicianPersistence musicianRepository = new MusicianRepository();
    private static final BandPersistence bandRepository = new BandRepository();
    private static final EventPersistence eventRepository = new EventRepository();

    @Override
    public BandPersistence getBandPersistence() {

        return bandRepository;
    }

    @Override
    public EventPersistence getEventPersistence() {
        return eventRepository;
    }

    @Override
    public MusicianPersistence getMusicianPersistence() {
        return musicianRepository;
    }
}
