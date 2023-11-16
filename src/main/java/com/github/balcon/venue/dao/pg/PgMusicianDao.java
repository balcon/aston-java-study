package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.MusicianDao;
import com.github.balcon.venue.entity.Musician;

import java.util.List;
import java.util.Optional;

public class PgMusicianDao extends AbstractDao<Musician> implements MusicianDao {
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
    private static final Builder<Musician> MUSICIAN_AND_BAND_BUILDER = resultSet ->
            Musician.builder()
                    .id(resultSet.getInt("m_id"))
                    .name(resultSet.getString("m_name"))
                    .role(resultSet.getString("m_role"))
                    .band(PgBandDao.BAND_BUILDER.build(resultSet)).build();

    @Override
    public Musician save(Musician musician) {
        int id = insert(INSERT, musician.getName(), musician.getRole(), musician.getBand().getId());
        musician.setId(id);
        return musician;
    }

    @Override
    public Optional<Musician> find(int id) {
        return select(SELECT + " WHERE m.id = ?", id, MUSICIAN_AND_BAND_BUILDER);
    }

    @Override
    public List<Musician> findAll() {
        return selectAll(SELECT, MUSICIAN_AND_BAND_BUILDER);
    }

    @Override
    public List<Musician> findByName(String name) {
        return selectFiltered(SELECT + " WHERE m.name ILIKE ?", MUSICIAN_AND_BAND_BUILDER, "%" + name + "%");
    }

    @Override
    public boolean update(Musician musician) {
        return update(UPDATE, musician.getId(), musician.getName(),
                musician.getRole(), musician.getBand().getId());
    }

    @Override
    public boolean delete(int id) {
        return delete(DELETE, id);
    }
}
