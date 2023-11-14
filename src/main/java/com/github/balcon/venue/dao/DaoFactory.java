package com.github.balcon.venue.dao;

public interface DaoFactory {
    BandDao getBandDao();

    EventDao getEventDao();

    MusicianDao getMusicianDao();
}
