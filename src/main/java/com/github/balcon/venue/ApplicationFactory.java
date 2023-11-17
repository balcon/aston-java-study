package com.github.balcon.venue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.balcon.venue.persistence.PersistenceFactory;
import com.github.balcon.venue.persistence.dao.pg.PgDaoFactory;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationFactory {
    private final PersistenceFactory persistenceFactory = new PgDaoFactory();
    private final ObjectMapper jsonMapper = new ObjectMapper();

    static {
        configureMapper();
    }

    public PersistenceFactory getPersistenceFactory() {
        return persistenceFactory;
    }

    public ObjectMapper getJsonMapper() {
        return jsonMapper;
    }

    private static void configureMapper() {
        jsonMapper.registerModule(new JavaTimeModule());
        jsonMapper.setDateFormat(new StdDateFormat());
    }
}
