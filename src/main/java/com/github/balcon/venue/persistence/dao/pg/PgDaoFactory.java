package com.github.balcon.venue.persistence.dao.pg;

import com.github.balcon.venue.persistence.BandPersistence;
import com.github.balcon.venue.persistence.PersistenceFactory;
import com.github.balcon.venue.persistence.EventPersistence;
import com.github.balcon.venue.persistence.MusicianPersistence;

public class PgDaoFactory implements PersistenceFactory {
    private final PgMusicianDao pgMusicianDao = new PgMusicianDao();
    private final BandPersistence pgBandDao = new PgBandDao(PgMusicianDao.MUSICIAN_BUILDER);
    private final EventPersistence pgEventDao = new PgEventDao(PgBandDao.BAND_BUILDER);

    @Override
    public BandPersistence getBandPersistence() {
        return pgBandDao;
    }

    @Override
    public EventPersistence getEventPersistence() {
        return pgEventDao;
    }

    @Override
    public MusicianPersistence getMusicianPersistence() {
        return pgMusicianDao;
    }
}
