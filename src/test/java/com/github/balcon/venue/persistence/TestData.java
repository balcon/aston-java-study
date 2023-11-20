package com.github.balcon.venue.persistence;

import com.github.balcon.venue.utils.ConnectionManager;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@UtilityClass
public class TestData {
    public void populate() {
        execute("""
                INSERT INTO band (id, name)
                VALUES (100, 'Band Number One'),
                       (101, 'Second Band'),
                       (102, 'Third Band'),
                       (103, 'Fourth Band');
                            
                INSERT INTO event (id, name, date_time)
                VALUES (100, 'First Event', '2023-12-31 10:00'),
                       (101, 'Second Event', '2024-01-01 10:00'),
                       (102, 'Third Event', '2023-12-31 22:00');
                       
                INSERT INTO event_band (event_id, band_id)
                VALUES  (100, 100),
                        (100, 101),
                        (100, 102),
                        (101, 103),
                        (101, 100),
                        (102, 101),
                        (102, 102);
                        
                       
                INSERT INTO musician (id, name, role, band_id)
                VALUES (100, 'Musician #100', 'Singer', 100),
                        (101, 'Musician #101', 'Guitar player', 100),
                        (102, 'Musician #102', 'Bass player', 100),
                        (103, 'Musician John #103', 'Drummer', 100),
                        (104, 'Musician #104', 'Singer', 101),
                        (105, 'Musician John #105', 'Guitar player', 101),
                        (106, 'Musician #106', 'One-man-band', 102),
                        (107, 'John Musician #107', 'Keyboard player', 101);
                """);
    }

    public void erase() {
        execute("""
                DELETE FROM event;
                DELETE FROM musician;
                DELETE FROM band;
                """);
    }

    public void randomize() {
        execute("""                        
                INSERT INTO indexed_entity (random)
                SELECT FLOOR(RANDOM() * 100000)
                FROM generate_series(1, 3000000);
                """);

        execute("""                        
                INSERT INTO unindexed_entity (random)
                SELECT FLOOR(RANDOM() * 100000)
                FROM generate_series(1, 3000000);
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
