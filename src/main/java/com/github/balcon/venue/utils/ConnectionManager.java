package com.github.balcon.venue.utils;

import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@UtilityClass
public class ConnectionManager {
    private Map<String, String> dbProperties;

    static {
        loadDriver();
        loadProperties();
    }

    public Connection connection() {
        try {
            String url = dbProperties.get("url");
            String username = dbProperties.get("username");
            String password = dbProperties.get("password");
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProperties() {
        Yaml yaml = new Yaml();
        dbProperties = yaml.load(ConnectionManager.class.getClassLoader().getResourceAsStream("db.yaml"));
    }
}
