package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.function.Function;

public abstract class AbstractRepository {
    protected <R> R execute(Function<Session, R> action) {
        Session session = HibernateUtil.session();
        try (session) {
            session.beginTransaction();
            R result = action.apply(session);
            session.getTransaction().commit();
            return result;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
