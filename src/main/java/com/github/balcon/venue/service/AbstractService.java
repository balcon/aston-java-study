package com.github.balcon.venue.service;

import java.util.List;
import java.util.Optional;

public interface AbstractService<T, R> {
    Optional<R> findById(int id);

    List<R> findAll();

    R save(T entity);

    void update(int id, T entity);

    void delete(int id);
}
