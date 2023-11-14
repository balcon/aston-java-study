package com.github.balcon.venue;

import com.github.balcon.venue.dao.DaoFactory;
import com.github.balcon.venue.dao.pg.PgDaoFactory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationFactory {
    public DaoFactory getDaoFactory() {
        return new PgDaoFactory();
    }
}
