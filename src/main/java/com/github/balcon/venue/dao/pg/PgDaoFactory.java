package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.BandDao;
import com.github.balcon.venue.dao.DaoFactory;
import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.dao.MusicianDao;

public class PgDaoFactory implements DaoFactory {
    @Override
    public BandDao getBandDao() {
        return null;
    }

    @Override
    public EventDao getEventDao() {
        return new PgEventDao();
    }

    @Override
    public MusicianDao getMusicianDao() {
        return null;
    }
}
