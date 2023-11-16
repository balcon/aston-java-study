package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PgEventDao extends AbstractDao<Event> implements EventDao {
    private static final String INSERT = "INSERT INTO event (name, date_time) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE event SET name = ?, date_time = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM event WHERE id = ?";
    private static final String SELECT = """
            SELECT e.id e_id, e.name e_name, e.date_time e_date_time, b.id b_id, b.name b_name
                FROM event e
                    LEFT JOIN event_band eb ON e.id = eb.event_id
                    LEFT JOIN band b ON eb.band_id = b.id""";

    private static final Builder<Event> EVENT_BUILDER = resultSet ->
            Event.builder()
                    .id(resultSet.getInt("e_id"))
                    .name(resultSet.getString("e_name"))
                    .dateTime(resultSet.getTimestamp("e_date_time").toLocalDateTime()).build();

    @Override
    public Event save(Event event) {
        int id = insert(INSERT, event.getName(), event.getDateTime());
        event.setId(id);
        return event;
    }

    @Override
    public Optional<Event> find(int id) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT + " WHERE e.id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Event event = null;
            if (resultSet.next()) {
                event = EVENT_BUILDER.build(resultSet);

                if (resultSet.getObject("b_id") != null) {
                    event.addBand(PgBandDao.BAND_BUILDER.build(resultSet));
                    while (resultSet.next()) {
                        event.addBand(PgBandDao.BAND_BUILDER.build(resultSet));
                    }
                }
            }
            return Optional.ofNullable(event);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> findAll() {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, Event> events = new LinkedHashMap<>();
            while (resultSet.next()) {
                Event event = EVENT_BUILDER.build(resultSet);
                int eventId = event.getId();
                events.putIfAbsent(eventId, event);
                if (resultSet.getObject("b_id") != null) {
                    events.get(eventId).addBand(PgBandDao.BAND_BUILDER.build(resultSet));
                }
            }
            return new ArrayList<>(events.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> findByDate(LocalDate date) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT + " WHERE DATE(date_time) = ?")) {
            statement.setObject(1, date);
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, Event> events = new LinkedHashMap<>();
            while (resultSet.next()) {
                Event event = EVENT_BUILDER.build(resultSet);
                int eventId = event.getId();
                events.putIfAbsent(eventId, event);
                if (resultSet.getObject("b_id") != null) {
                    events.get(eventId).addBand(PgBandDao.BAND_BUILDER.build(resultSet));
                }
            }
            return new ArrayList<>(events.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Event event) {
        return update(UPDATE, event.getId(), event.getName(), event.getDateTime());
    }

    @Override
    public boolean delete(int id) {
        return delete(DELETE, id);
    }
}
