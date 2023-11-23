package com.github.balcon.venue.service;

import java.util.List;

public interface AbstractService<R, W> {
    R findById(int id);

    List<R> findAll();

    R save(W writeDto);

    void update(int id, W dto);

    void delete(int id);
}
