package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.BandDao;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PgBandDao extends AbstractDao<Band> implements BandDao {
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

    @Override
    public Band save(Band band) {
        int id = insert(INSERT, band.getName());
        band.setId(id);
        return band;
    }

    @Override
    public Optional<Band> find(int id) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT + " WHERE b.id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Band band = null;
            if (resultSet.next()) {
                band = BAND_BUILDER.build(resultSet);
                if (resultSet.getObject("m_id") != null) {
                    band.addMusician(PgMusicianDao.MUSICIAN_BUILDER.build(resultSet));
                    while (resultSet.next()) {
                        band.addMusician(PgMusicianDao.MUSICIAN_BUILDER.build(resultSet));
                    }
                }
            }
            return Optional.ofNullable(band);
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Band> findAll() {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            Map<Integer, Band> bands = new LinkedHashMap<>();
            while (resultSet.next()) {
                Band band = BAND_BUILDER.build(resultSet);
                int bandId = band.getId();
                bands.putIfAbsent(bandId, band);
                if (resultSet.getObject("m_id") != null) {
                    bands.get(bandId).addMusician(PgMusicianDao.MUSICIAN_BUILDER.build(resultSet));
                }
            }
            return new ArrayList<>(bands.values());
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
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
