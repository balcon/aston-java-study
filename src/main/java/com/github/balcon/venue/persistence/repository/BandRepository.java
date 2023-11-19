package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.persistence.BandPersistence;
import com.github.balcon.venue.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;

import java.util.List;
import java.util.Optional;

public class BandRepository implements BandPersistence {
    @Override
    public Band save(Band band) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            session.persist(band);
            session.getTransaction().commit();
            return band;
        }
    }

    // Avoid LazyInitException with EntityGraph
    @Override
    public Optional<Band> find(int id) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            RootGraph<Band> bandGraph = session.createEntityGraph(Band.class);
            bandGraph.addAttributeNode(Band.Fields.musicians);
            Band band = session.byId(Band.class)
                    .withFetchGraph(bandGraph)
                    .load(id);
            session.getTransaction().commit();
            return Optional.ofNullable(band);
        }
    }

    @Override
    public List<Band> findAll() {
        String query = "SELECT b FROM Band b LEFT JOIN FETCH b.musicians";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            List<Band> list = session.createQuery(query, Band.class)
                    .list();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public boolean update(Band band) {
        if (!band.hasId()) {
            return false;
        }
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            if (session.get(Band.class, band.getId()) == null) {
                return false;
            }
            session.merge(band);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean delete(int id) {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            Band band = session.get(Band.class, id);
            if (band == null) {
                return false;
            }
            session.remove(band);
            session.getTransaction().commit();
            return true;
        }
    }
}
