package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.persistence.EventPersistence;
import com.github.balcon.venue.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EventRepository implements EventPersistence {
    @Override
    public Event save(Event event) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            session.persist(event);
            session.getTransaction().commit();
            return event;
        }
    }

    @Override
    public Optional<Event> find(int id) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            Event event = getEventWithBands(id, session);
            session.getTransaction().commit();
            return Optional.ofNullable(event);
        }
    }

    @Override
    public List<Event> findAll() {
        String query = "SELECT e FROM Event e LEFT JOIN FETCH e.bands";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            List<Event> list = session.createQuery(query, Event.class)
                    .list();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public List<Event> findByDate(LocalDate date) {
        String query = "SELECT e FROM Event e LEFT JOIN FETCH e.bands WHERE DATE(e.dateTime) = :date";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            List<Event> list = session.createQuery(query, Event.class)
                    .setParameter("date", date)
                    .list();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public boolean update(Event event) {
        if (!event.hasId()) {
            return false;
        }
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            if (session.get(Event.class, event.getId()) == null) {
                return false;
            }
            session.merge(event);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean delete(int id) {
        String query = "DELETE FROM Event e WHERE e.id = :id";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            int result = session.createMutationQuery(query)
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            return result == 1;
        }
    }

    // TODO: 19.11.2023 Check queries
    @Override
    public boolean addBand(int eventId, int bandId) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            Event event = getEventWithBands(eventId, session);
            Band band = session.get(Band.class, bandId);
            if (event == null || band == null) {
                return false;
            }
            event.addBand(band);
            session.getTransaction().commit();
            return true;
        }
    }

    // TODO: 19.11.2023 Check queries
    @Override
    public boolean removeBand(int eventId, int bandId) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            Event event = getEventWithBands(eventId, session);
            Band band = session.get(Band.class, bandId);
            if (event == null || band == null) {
                return false;
            }
            event.removeBand(band);
            session.getTransaction().commit();
            return true;
        }
    }

    private static Event getEventWithBands(int id, Session session) {
        RootGraph<Event> eventGraph = session.createEntityGraph(Event.class);
        eventGraph.addAttributeNode(Event.Fields.bands);
        return session.byId(Event.class).withFetchGraph(eventGraph).load(id);
    }
}
