package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.dao.MusicianDao;
import com.github.balcon.venue.entity.Musician;

import java.util.List;
import java.util.Optional;

public class PgMusicianDao extends AbstractDao<Musician> implements MusicianDao {
    private static final String INSERT = "INSERT INTO musician (name, role, band_id) VALUES (?, ?, ?)";
    private static final String SELECT = "SELECT id, name, role, band_id FROM musician";
    private static final String UPDATE = "UPDATE musician SET name = ?, role = ?, band_id = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM musician WHERE id = ?";

    private static final Builder<Musician> MUSICIAN_BUILDER = resultSet ->
            Musician.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .role(resultSet.getString("role"))
                    .bandId(resultSet.getInt("band_id")).build();

    @Override
    public Musician save(Musician musician) {
        int id = insert(INSERT, musician.getName(), musician.getRole(), musician.getBandId());
        musician.setId(id);
        return musician;
    }

    @Override
    public Optional<Musician> find(int id) {
        return select(SELECT, id, MUSICIAN_BUILDER);
    }

    @Override
    public List<Musician> findAll() {
        return selectAll(SELECT, MUSICIAN_BUILDER);
    }

    @Override
    public List<Musician> findByName(String name) {
        return selectFiltered(SELECT + " WHERE name ILIKE ?", MUSICIAN_BUILDER, "%" + name + "%");
    }

    @Override
    public boolean update(Musician musician) {
        return update(UPDATE, musician.getId(), musician.getName(), musician.getRole(), musician.getBandId());
    }

    @Override
    public boolean delete(int id) {
        return delete(DELETE, id);
    }
}
