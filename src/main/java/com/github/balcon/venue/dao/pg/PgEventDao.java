package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.EventDao;
import com.github.balcon.venue.entity.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PgEventDao extends AbstractDao<Event> implements EventDao {
    private static final String INSERT = "INSERT INTO event (name, date_time) VALUES (?, ?)";
    private static final String SELECT = "SELECT id, name, date_time FROM event";
    private static final String UPDATE = "UPDATE event SET name = ?, date_time = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM event WHERE id = ?";
    private static final Builder<Event> EVENT_BUILDER = resultSet ->
            Event.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .dateTime(resultSet.getTimestamp("date_time").toLocalDateTime()).build();

    @Override
    public Event save(Event event) {
        int id = insert(INSERT, event.getName(), event.getDateTime());
        event.setId(id);
        return event;
    }

    @Override
    public Optional<Event> find(int id) {
        return select(SELECT, id, EVENT_BUILDER);
    }

    @Override
    public List<Event> findAll() {
        return selectAll(SELECT, EVENT_BUILDER);
    }

    @Override
    public List<Event> findByDate(LocalDate date) {
        return selectFiltered(SELECT + " WHERE DATE(date_time) = ?", EVENT_BUILDER, date);
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
