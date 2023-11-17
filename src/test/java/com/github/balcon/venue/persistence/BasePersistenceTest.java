package com.github.balcon.venue.persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BasePersistenceTest {
    @BeforeAll
    static void beforeAll() {
        TestData.erase();
    }

    @BeforeEach
    void setUp() {
        TestData.populate();
    }

    @AfterEach
    void tearDown() {
        TestData.erase();
    }
}
