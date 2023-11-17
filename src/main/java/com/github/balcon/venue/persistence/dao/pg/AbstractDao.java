package com.github.balcon.venue.persistence.dao.pg;

import com.github.balcon.venue.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao {
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

    protected boolean execute(String sql, Object... properties) {
        try (Connection connection = ConnectionManager.connection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < properties.length; i++) {
                statement.setObject(i + 1, properties[i]);
            }
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
