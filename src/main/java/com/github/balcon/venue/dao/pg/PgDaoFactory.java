package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.BandDao;
import com.github.balcon.venue.dao.DaoFactory;
import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.dao.MusicianDao;

public class PgDaoFactory implements DaoFactory {
    private final BandDao bandDao = new PgBandDao();
    private final PgEventDao pgEventDao = new PgEventDao();
    private final PgMusicianDao pgMusicianDao = new PgMusicianDao();

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
