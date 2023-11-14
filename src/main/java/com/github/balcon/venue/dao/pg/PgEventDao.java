package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.model.Event;
import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PgEventDao implements EventDao {
    private static final String INSERT = "INSERT INTO event (name, date_time) VALUES (?, ?)";
    private static final String SELECT = "SELECT id, name, date_time FROM event";
    private static final String UPDATE = "UPDATE event SET name = ?, date_time = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM event WHERE id = ?";

    @Override
    public Event save(Event event) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS)) {
            statement.setString(1, event.getName());
            statement.setTimestamp(2, Timestamp.valueOf(event.getDateTime()));
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                event.setId(generatedKeys.getInt("id"));
            }

            return event;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Event> find(int id) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT + " WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Event event = null;
            if (resultSet.next()) {
                event = buildEvent(resultSet);
            }

            return Optional.ofNullable(event);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> findAll() {
        List<Event> events = new ArrayList<>();
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                events.add(buildEvent(resultSet));
            }

            return events;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> findByDate(LocalDate date) {
        List<Event> events = new ArrayList<>();
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT + " WHERE DATE(date_time) = ?")) {
            statement.setDate(1, Date.valueOf(date));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                events.add(buildEvent(resultSet));
            }
            return events;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Event buildEvent(ResultSet resultSet) throws SQLException {
        return Event.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .dateTime(resultSet.getTimestamp("date_time").toLocalDateTime()).build();
    }

    @Override
    public boolean update(Event event) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, event.getName());
            statement.setTimestamp(2, Timestamp.valueOf(event.getDateTime()));
            statement.setInt(3, event.getId());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
