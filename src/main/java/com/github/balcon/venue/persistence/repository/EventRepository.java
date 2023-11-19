package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.persistence.EventPersistence;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EventRepository extends AbstractRepository implements EventPersistence {
    @Override
    public Event save(Event event) {
        return execute(session -> {
            session.persist(event);
            return event;
        });
    }

    @Override
    public Optional<Event> find(int id) {
        return execute(session -> Optional.ofNullable(getEventWithBands(id, session)));
    }

    @Override
    public List<Event> findAll() {
        String query = "SELECT e FROM Event e LEFT JOIN FETCH e.bands";
        return execute(session -> session.createQuery(query, Event.class)
                .list());
    }

    @Override
    public List<Event> findByDate(LocalDate date) {
        String query = "SELECT e FROM Event e LEFT JOIN FETCH e.bands WHERE DATE(e.dateTime) = :date";
        return execute(session -> session.createQuery(query, Event.class)
                .setParameter("date", date)
                .list());
    }

    @Override
    public boolean update(Event event) {
        if (!event.hasId()) {
            return false;
        }
        return execute(session -> {
            if (session.get(Event.class, event.getId()) == null) {
                return false;
            }
            session.merge(event);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Event e WHERE e.id = :id";
        return execute(session -> session.createMutationQuery(query)
                .setParameter("id", id)
                .executeUpdate() == 1);
    }

    @Override
    public boolean addBand(int eventId, int bandId) {
        return execute(session -> {
            Event event = getEventWithBands(eventId, session);
            Band band = session.get(Band.class, bandId);
            if (event == null || band == null) {
                return false;
            }
            event.addBand(band);
            return true;
        });
    }

    @Override
    public boolean removeBand(int eventId, int bandId) {
        return execute(session -> {
            Event event = getEventWithBands(eventId, session);
            Band band = session.get(Band.class, bandId);
            if (event == null || band == null) {
                return false;
            }
            event.removeBand(band);
            return true;
        });
    }

    private static Event getEventWithBands(int id, Session session) {
        RootGraph<Event> eventGraph = session.createEntityGraph(Event.class);
        eventGraph.addAttributeNode(Event.Fields.bands);
        return session.byId(Event.class).withFetchGraph(eventGraph).load(id);
    }
}
