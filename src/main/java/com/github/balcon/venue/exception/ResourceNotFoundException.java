package com.github.balcon.venue.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final int id;

    public String getMessage() {
        return "Resource [%s] with id [%d] not found".formatted(resource, id);
    }
}
