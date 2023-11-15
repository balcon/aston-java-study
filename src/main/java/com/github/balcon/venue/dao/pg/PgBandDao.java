package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.BandDao;
import com.github.balcon.venue.entity.Band;

import java.util.List;
import java.util.Optional;

public class PgBandDao extends AbstractDao<Band> implements BandDao {
    private static final String INSERT = "INSERT INTO band (name) VALUES (?)";
    private static final String SELECT = "SELECT id, name FROM band";
    private static final String UPDATE = "UPDATE band SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM band WHERE id = ?";
    private static final Builder<Band> BAND_BUILDER = resultSet ->
            Band.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name")).build();

    @Override
    public Band save(Band band) {
        int id = insert(INSERT, band.getName());
        band.setId(id);
        return band;
    }

    @Override
    public Optional<Band> find(int id) {
        return select(SELECT, id, BAND_BUILDER);
    }

    @Override
    public List<Band> findAll() {
        return selectAll(SELECT, BAND_BUILDER);
    }

    @Override
    public boolean update(Band band) {
        return update(UPDATE, band.getId(), band.getName());
    }

    @Override
    public boolean delete(int id) {
        return delete(DELETE, id);
    }
}
