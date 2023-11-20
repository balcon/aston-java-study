package com.github.balcon.venue.persistence;

import com.github.balcon.venue.entity.indexes.IndexedEntity;
import com.github.balcon.venue.entity.indexes.UnindexedEntity;
import com.github.balcon.venue.utils.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PerformanceTest {
    @BeforeAll
    @Disabled
    static void beforeAll() {
        TestData.randomize();
    }

    @Test
    @Order(1)
    void selectIndexedFirst() {
        String query = "SELECT e FROM IndexedEntity e WHERE e.random = 1";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            session.createQuery(query, IndexedEntity.class)
                    .list();
            session.getTransaction().commit();
        }
    }

    @Test
    @Order(2)
    void selectUnindexedFirst() {
        String query = "SELECT e FROM UnindexedEntity e WHERE e.random = 1";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            session.createQuery(query, UnindexedEntity.class)
                    .list();
            session.getTransaction().commit();
        }
    }

    @Test
    @Order(3)
    void selectIndexedSecond() {
        String query = "SELECT e FROM IndexedEntity e WHERE e.random = 1";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            session.createQuery(query, IndexedEntity.class)
                    .list();
            session.getTransaction().commit();
        }
    }

    @Test
    @Order(4)
    void selectUnindexedSecond() {
        String query = "SELECT e FROM UnindexedEntity e WHERE e.random = 1";
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            session.createQuery(query, UnindexedEntity.class)
                    .list();
            session.getTransaction().commit();
        }
    }
}
