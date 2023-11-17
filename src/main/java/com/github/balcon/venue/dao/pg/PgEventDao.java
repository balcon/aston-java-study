package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class PgEventDao extends AbstractDao implements EventDao {
    private static final String INSERT = "INSERT INTO event (name, date_time) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE event SET name = ?, date_time = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM event WHERE id = ?";
    private static final String SELECT = """
            SELECT e.id e_id, e.name e_name, e.date_time e_date_time, b.id b_id, b.name b_name
                FROM event e
                    LEFT JOIN event_band eb ON e.id = eb.event_id
                    LEFT JOIN band b ON eb.band_id = b.id""";
    private static final String ADD_BAND = "INSERT INTO event_band (event_id, band_id) VALUES (?,?)";
    private static final String REMOVE_BAND = "DELETE FROM event_band WHERE event_id = ? AND band_id = ?";

    private final Builder<Band> bandBuilder;

    public PgEventDao(Builder<Band> bandBuilder) {
        this.bandBuilder = bandBuilder;
    }

    @Override
    public Event save(Event event) {
        int id = insert(INSERT, event.getName(), event.getDateTime());
        event.setId(id);
        return event;
    }

    @Override
    public Optional<Event> find(int id) {
        List<Event> events = select(SELECT + " WHERE e.id = ?", id);
        if (events.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(events.get(0));
    }

    @Override
    public List<Event> findAll() {
        return select(SELECT);
    }

    @Override
    public List<Event> findByDate(LocalDate date) {
        return select(SELECT + " WHERE DATE(date_time) = ?", date);
    }

    @Override
    public boolean update(Event event) {
        return execute(UPDATE, event.getName(), event.getDateTime(), event.getId());
    }

    @Override
    public boolean delete(int id) {
        return execute(DELETE, id);
    }

    @Override
    public boolean addBand(int eventId, int bandId) {
        return execute(ADD_BAND, eventId, bandId);
    }

    @Override
    public boolean removeBand(int eventId, int bandId) {
        return execute(REMOVE_BAND, eventId, bandId);
    }

    private Event build(ResultSet resultSet) throws SQLException {
        return Event.builder()
                .id(resultSet.getInt("e_id"))
                .name(resultSet.getString("e_name"))
                .dateTime(resultSet.getTimestamp("e_date_time").toLocalDateTime()).build();
    }

    private List<Event> select(String sql, Object... properties) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < properties.length; i++) {
                statement.setObject(i + 1, properties[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, Event> events = new LinkedHashMap<>();
            while (resultSet.next()) {
                Event event = build(resultSet);
                int eventId = event.getId();
                events.putIfAbsent(eventId, event);
                if (resultSet.getObject("b_id") != null) {
                    events.get(eventId).addBand(bandBuilder.build(resultSet));
                }
            }
            return new ArrayList<>(events.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
