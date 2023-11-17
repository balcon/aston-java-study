package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.MusicianDao;
import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PgMusicianDao extends AbstractDao implements MusicianDao {
    private static final String INSERT = "INSERT INTO musician (name, role, band_id) VALUES (?, ?, ?)";
    private static final String UPDATE = "UPDATE musician SET name = ?, role = ?, band_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM musician WHERE id = ?";
    private static final String SELECT = """
            SELECT m.id m_id, m.name m_name, m.role m_role, b.id b_id, b.name b_name
                FROM musician m
                    JOIN band b ON m.band_id = b.id""";

    protected static final Builder<Musician> MUSICIAN_BUILDER = resultSet ->
            Musician.builder()
                    .id(resultSet.getInt("m_id"))
                    .name(resultSet.getString("m_name"))
                    .role(resultSet.getString("m_role")).build();

    @Override
    public Musician save(Musician musician) {
        int id = insert(INSERT, musician.getName(), musician.getRole(), musician.getBand().getId());
        musician.setId(id);
        return musician;
    }

    @Override
    public Optional<Musician> find(int id) {
        List<Musician> musicians = select(SELECT + " WHERE m.id = ?", id);
        if (musicians.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(musicians.get(0));
    }

    @Override
    public List<Musician> findAll() {
        return select(SELECT);
    }

    @Override
    public List<Musician> findByName(String name) {
        return select(SELECT + " WHERE m.name ILIKE ?", "%" + name + "%");
    }

    @Override
    public boolean update(Musician m) {
        return execute(UPDATE, m.getName(), m.getRole(), m.getBand().getId(), m.getId());
    }

    @Override
    public boolean delete(int id) {
        return execute(DELETE, id);
    }

    private Musician build(ResultSet resultSet) throws SQLException {
        return Musician.builder()
                .id(resultSet.getInt("m_id"))
                .name(resultSet.getString("m_name"))
                .role(resultSet.getString("m_role"))
                .band(PgBandDao.BAND_BUILDER.build(resultSet)).build();
    }

    private List<Musician> select(String sql, Object... properties) {
        List<Musician> musicians = new ArrayList<>();
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < properties.length; i++) {
                statement.setObject(i + 1, properties[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                musicians.add(build(resultSet));
            }

            return musicians;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
