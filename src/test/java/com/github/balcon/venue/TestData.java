package com.github.balcon.venue;

import com.github.balcon.venue.utils.ConnectionManager;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@UtilityClass
public class TestData {
    public void populate() {
        execute("""
                INSERT INTO band
                VALUES (100, 'Band Number One'),
                       (101, 'Second Band'),
                       (102, 'Third Band'),
                       (103, 'Fourth Band');
                            
                INSERT INTO event
                VALUES (100, 'First Event', '2023-12-31 10:00'),
                       (101, 'Second Evevnt', '2024-01-01 10:00'),
                       (102, 'Third Event', '2023-12-31 22:00');
                """);
    }

    public void erase() {
        execute("""
                DELETE FROM event;
                DELETE FROM band;
                """);
    }

    private static void execute(String sql) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
