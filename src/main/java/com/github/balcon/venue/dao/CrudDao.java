package com.github.balcon.venue.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    T save(T T);

    Optional<T> find(int id);

    List<T> findAll();

    boolean update(int id, T T);

    boolean delete(int id);
}
