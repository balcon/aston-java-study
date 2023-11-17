package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {
    protected int insert(String sql, Object... properties) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < properties.length; i++) {
                statement.setObject(i + 1, properties[i]);
            }
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();
            return generatedKeys.getInt("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Optional<T> select(String sql, int id, Builder<T> builder) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            T entity = resultSet.next()
                    ? builder.build(resultSet)
                    : null;

            return Optional.ofNullable(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<T> selectAll(String sql, Builder<T> builder) {
        return selectFiltered(sql, builder);
    }

    protected List<T> selectFiltered(String sql, Builder<T> builder, Object... conditions) {
        List<T> entities = new ArrayList<>();
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < conditions.length; i++) {
                statement.setObject(i + 1, conditions[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(builder.build(resultSet));
            }

            return entities;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean update(String sql, Integer id, Object... properties) {
        if (id == null) {
            return false;
        }
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < properties.length; i++) {
                statement.setObject(i + 1, properties[i]);
            }
            statement.setInt(properties.length + 1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean delete(String sql, int id) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected int execute(String sql, Object... properties) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < properties.length; i++) {
                statement.setObject(i + 1, properties[i]);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
