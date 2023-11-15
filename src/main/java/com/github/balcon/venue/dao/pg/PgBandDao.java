package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.BandDao;
import com.github.balcon.venue.model.Band;
import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PgBandDao implements BandDao {
    private static final String INSERT = "INSERT INTO band (name) VALUES (?)";
    private static final String SELECT = "SELECT id, name FROM band";
    private static final String UPDATE = "UPDATE band SET name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM band WHERE id = ?";

    @Override
    // TODO: 15.11.2023 erase id
    public Band save(Band band) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS)) {
            statement.setString(1, band.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                band.setId(generatedKeys.getInt("id"));
            }

            return band;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Band> find(int id) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT + " WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            Band band = null;
            if (resultSet.next()) {
                band = buildBand(resultSet);
            }

            return Optional.ofNullable(band);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Band> findAll() {
        List<Band> bands = new ArrayList<>();
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(SELECT)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                bands.add(buildBand(resultSet));
            }

            return bands;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Band buildBand(ResultSet resultSet) throws SQLException {
        return Band.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name")).build();
    }

    @Override
    public boolean update(Band entity) {
        // TODO: 15.11.2023 Check id
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(UPDATE)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getId());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(DELETE)) {
            statement.setInt(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
