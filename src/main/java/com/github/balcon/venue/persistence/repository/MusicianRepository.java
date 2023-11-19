package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.persistence.MusicianPersistence;
import com.github.balcon.venue.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class MusicianRepository implements MusicianPersistence {
    @Override
    public Musician save(Musician musician) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            session.persist(musician);
            session.getTransaction().commit();
            return musician;
        }
    }

    // Avoid LazyInitException with JOIN FETCH
    @Override
    public Optional<Musician> find(int id) {
        String query = "SELECT m FROM Musician m JOIN FETCH m.band WHERE m.id = :id";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            Optional<Musician> musician = session.createQuery(query, Musician.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
            session.getTransaction().commit();
            return musician;
        }
    }

    @Override
    public List<Musician> findAll() {
        String query = "SELECT m FROM Musician m JOIN FETCH m.band";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            List<Musician> musicians = session.createQuery(query, Musician.class)
                    .list();
            session.getTransaction().commit();
            return musicians;
        }
    }

    @Override
    public List<Musician> findByName(String name) {
        String query = "SELECT m FROM Musician m JOIN FETCH m.band WHERE m.name ILIKE :name";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            List<Musician> musicians = session.createQuery(query, Musician.class)
                    .setParameter("name", "%" + name + "%")
                    .list();
            session.getTransaction().commit();
            return musicians;
        }
    }

    @Override
    public boolean update(Musician musician) {
        if (!musician.hasId()) {
            return false;
        }
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            if (session.get(Musician.class, musician.getId()) == null) {
                return false;
            }
            session.merge(musician);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean delete(int id) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            Musician musician = session.get(Musician.class, id);
            if (musician == null) {
                return false;
            }
            session.remove(musician);
            session.getTransaction().commit();
            return true;
        }
    }
}
