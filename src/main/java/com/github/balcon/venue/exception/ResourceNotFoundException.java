package com.github.balcon.venue.exception;

import lombok.RequiredArgsConstructor;

/**
 * Exception if resource not found.
 *
 * @author Konstantin Balykov
 */

@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
  private final String resource;
  private final int id;

  public String getMessage() {
    return "Resource [%s] with id [%d] not found".formatted(resource, id);
  }
}
