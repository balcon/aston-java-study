package com.github.balcon.venue.persistence.dao.pg;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Builder<T> {
    T build(ResultSet resultSet) throws SQLException;
}
