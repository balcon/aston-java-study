package com.github.balcon.venue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.balcon.venue.dao.DaoFactory;
import com.github.balcon.venue.dao.pg.PgDaoFactory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationFactory {
    private final DaoFactory daoFactory = new PgDaoFactory();
    private final ObjectMapper jsonMapper = new ObjectMapper();

    static {
        configureMapper();
    }

    public DaoFactory getDaoFactory() {
        return daoFactory;
    }

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    private static void configureMapper() {
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.setDateFormat(new StdDateFormat());
    }
}
