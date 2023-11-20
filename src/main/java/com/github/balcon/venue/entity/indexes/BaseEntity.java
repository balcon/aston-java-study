package com.github.balcon.venue.entity.indexes;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Random;

@MappedSuperclass
public abstract class BaseEntity {
    protected static final int MAX_RANDOM = 10000;
    protected static final Random RANDOM = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
