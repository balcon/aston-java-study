package com.github.balcon.venue.persistence.dao.pg;

import com.github.balcon.venue.persistence.BandPersistence;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PgBandDao extends AbstractDao implements BandPersistence {
    private static final String INSERT = "INSERT INTO band (name) VALUES (?)";
    private static final String UPDATE = "UPDATE band SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM band WHERE id = ?";
    private static final String SELECT = """
            SELECT b.id AS b_id, b.name b_name, m.id m_id, m.name m_name, m.role m_role
                FROM band b
                    LEFT JOIN musician m ON b.id = m.band_id""";

    protected static final Builder<Band> BAND_BUILDER = resultSet ->
            Band.builder()
                    .id(resultSet.getInt("b_id"))
                    .name(resultSet.getString("b_name")).build();

    private final Builder<Musician> musicianBuilder;

    public PgBandDao(Builder<Musician> musicianBuilder) {
        this.musicianBuilder = musicianBuilder;
    }

    @Override
    public Band save(Band band) {
        int id = insert(INSERT, band.getName());
        band.setId(id);
        return band;
    }

    @Override
    public Optional<Band> find(int id) {
        List<Band> bands = select(SELECT + " WHERE b.id = ?", id);
        if (bands.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(bands.get(0));
    }

    @Override
    public List<Band> findAll() {
        return select(SELECT);
    }

    @Override
    public boolean update(Band band) {
        return execute(UPDATE, band.getName(), band.getId());
    }

    @Override
    public boolean delete(int id) {
        return execute(DELETE, id);
    }

    private List<Band> select(String sql, Object... properties) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < properties.length; i++) {
                statement.setObject(i + 1, properties[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, Band> bands = new LinkedHashMap<>();
            while (resultSet.next()) {
                Band band = BAND_BUILDER.build(resultSet);
                int bandId = band.getId();
                bands.putIfAbsent(bandId, band);
                if (resultSet.getObject("m_id") != null) {
                    bands.get(bandId).addMusician(musicianBuilder.build(resultSet));
                }
            }
            return new ArrayList<>(bands.values());
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
