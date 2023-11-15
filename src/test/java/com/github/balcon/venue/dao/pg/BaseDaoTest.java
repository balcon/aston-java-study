package com.github.balcon.venue.dao.pg;

import com.github.balcon.venue.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseDaoTest {
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
