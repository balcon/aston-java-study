package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.BandDao;
import com.github.balcon.venue.dao.DaoFactory;
import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.dao.MusicianDao;

public class PgDaoFactory implements DaoFactory {
    private final PgMusicianDao pgMusicianDao = new PgMusicianDao();
    private final BandDao bandDao = new PgBandDao(PgMusicianDao.MUSICIAN_BUILDER);
    private final PgEventDao pgEventDao = new PgEventDao(PgBandDao.BAND_BUILDER);

    @Override
    public BandDao getBandDao() {
        return bandDao;
    }

    @Override
    public EventDao getEventDao() {
        return pgEventDao;
    }

    @Override
    public MusicianDao getMusicianDao() {
        return pgMusicianDao;
    }
}
