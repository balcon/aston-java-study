package com.github.balcon.venue.service;

import java.util.List;

/**
 * Base crud-methods for services.
 *
 * @author Konstantin Balykov
 */

public interface AbstractService<R, W> {
  R findById(int id);

  List<R> findAll();

  R save(W writeDto);

  void update(int id, W dto);

  void delete(int id);
}
