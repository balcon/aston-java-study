package com.github.balcon.venue.utils;

import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class ConnectionManager {
    public Connection connection() {
        try {
            String url = "jdbc:postgresql://localhost:5433/postgres";
            String username = "postgres";
            String password = "postgres";
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
