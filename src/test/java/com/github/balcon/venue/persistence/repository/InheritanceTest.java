package com.github.balcon.venue.persistence.repository;

import com.github.balcon.venue.entity.equipment.Amplifier;
import com.github.balcon.venue.entity.equipment.Equipment;
import com.github.balcon.venue.entity.equipment.Mixer;
import com.github.balcon.venue.utils.HibernateUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InheritanceTest {
    @Test
    @Order(1)
    void persist() {
        Equipment yamahaMixer = Mixer.builder()
                .name("MGP32X")
                .manufacturer("Yamaha")
                .micChannels(24)
                .stereoChannels(2).build();
        Equipment allenHeathMixer = Mixer.builder()
                .name("ZED 24")
                .manufacturer("Allen & Heath")
                .micChannels(16)
                .stereoChannels(4).build();
        Equipment behringerAmp = Amplifier.builder()
                .name("KM 750")
                .manufacturer("Behringer")
                .power(750)
                .channels(2).build();
        Equipment yamahaAmp = Amplifier.builder()
                .name("PX5")
                .manufacturer("Yamaha")
                .power(500)
                .channels(2).build();

        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            session.persist(yamahaMixer);
            session.persist(allenHeathMixer);
            session.persist(behringerAmp);
            session.persist(yamahaAmp);
            session.getTransaction().commit();
        }
    }

    @Test
    @Order(2)
    void polymorphicQuery() {
        try (Session session = HibernateUtil.session()) {
            session.beginTransaction();
            Equipment equipment = session.get(Equipment.class, 2);
            System.out.println(equipment);
            session.getTransaction().commit();
        }
    }
}
