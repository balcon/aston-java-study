package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.persistence.BandPersistence;
import org.hibernate.graph.RootGraph;

import java.util.List;
import java.util.Optional;

public class BandRepository extends AbstractRepository implements BandPersistence {
    @Override
    public Band save(Band band) {
        return execute(session -> {
            session.persist(band);
            return band;
        });
    }

    // Avoid LazyInitException with EntityGraph
    @Override
    public Optional<Band> find(int id) {
        return execute(session -> {
            RootGraph<Band> bandGraph = session.createEntityGraph(Band.class);
            bandGraph.addAttributeNode(Band.Fields.musicians);
            return Optional.ofNullable(session.byId(Band.class)
                    .withFetchGraph(bandGraph)
                    .load(id));
        });
    }

    @Override
    public List<Band> findAll() {
        String query = "SELECT b FROM Band b LEFT JOIN FETCH b.musicians";
        return execute(session -> session.createQuery(query, Band.class)
                .list());
    }

    @Override
    public boolean update(Band band) {
        if (!band.hasId()) {
            return false;
        }
        return execute(session -> {
            if (session.get(Band.class, band.getId()) == null) {
                return false;
            }
            session.merge(band);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return execute(session -> {
            Band band = session.get(Band.class, id);
            if (band == null) {
                return false;
            }
            session.remove(band);
            return true;
        });
    }
}
