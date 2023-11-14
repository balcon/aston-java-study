package com.github.balcon.venue.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    T save(T entity);

    Optional<T> find(int id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(int id);
}
