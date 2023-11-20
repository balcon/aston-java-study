package com.github.balcon.venue.utils;

import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.entity.Musician;
import com.github.balcon.venue.entity.equipment.Amplifier;
import com.github.balcon.venue.entity.equipment.Mixer;
import com.github.balcon.venue.entity.indexes.IndexedEntity;
import com.github.balcon.venue.entity.indexes.UnindexedEntity;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@UtilityClass
public class HibernateUtil {
    private SessionFactory sessionFactory;

    static {
        buildSessionFactory();
    }

    public Session session() {
        return sessionFactory.openSession();
    }

    private void buildSessionFactory() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .build();

        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(Musician.class)
                .addAnnotatedClass(Band.class)
                .addAnnotatedClass(Event.class)
                .addAnnotatedClass(Amplifier.class)
                .addAnnotatedClass(Mixer.class)
                .addAnnotatedClass(IndexedEntity.class)
                .addAnnotatedClass(UnindexedEntity.class)
                .buildMetadata()
                .buildSessionFactory();
    }
}
