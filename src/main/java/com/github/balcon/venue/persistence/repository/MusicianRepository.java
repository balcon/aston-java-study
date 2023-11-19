package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.persistence.MusicianPersistence;

import java.util.List;
import java.util.Optional;

public class MusicianRepository extends AbstractRepository implements MusicianPersistence {
    @Override
    public Musician save(Musician musician) {
        return execute(session -> {
            session.persist(musician);
            return musician;
        });
    }

    // Avoid LazyInitException with JOIN FETCH
    @Override
    public Optional<Musician> find(int id) {
        String query = "SELECT m FROM Musician m JOIN FETCH m.band WHERE m.id = :id";
        return execute(session -> session.createQuery(query, Musician.class)
                .setParameter("id", id)
                .uniqueResultOptional());
    }

    @Override
    public List<Musician> findAll() {
        String query = "SELECT m FROM Musician m JOIN FETCH m.band";
        return execute(session -> session.createQuery(query, Musician.class)
                .list());
    }

    @Override
    public List<Musician> findByName(String name) {
        String query = "SELECT m FROM Musician m JOIN FETCH m.band WHERE m.name ILIKE :name";
        return execute(session -> session.createQuery(query, Musician.class)
                .setParameter("name", "%" + name + "%")
                .list());
    }

    @Override
    public boolean update(Musician musician) {
        if (!musician.hasId()) {
            return false;
        }
        return execute(session -> {
            if (session.get(Musician.class, musician.getId()) == null) {
                return false;
            }
            session.merge(musician);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return execute(session -> {
            Musician musician = session.get(Musician.class, id);
            if (musician == null) {
                return false;
            }
            session.remove(musician);
            return true;
        });
    }
}
