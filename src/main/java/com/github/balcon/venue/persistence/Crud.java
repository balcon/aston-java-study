package com.github.balcon.venue.persistence;

import java.util.List;
import java.util.Optional;

public interface Crud<T> {
    T save(T entity);

    Optional<T> find(int id);

    List<T> findAll();

    boolean update(T entity);

    boolean delete(int id);
}
