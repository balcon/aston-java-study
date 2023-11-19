package com.github.balcon.venue.servlet;

import org.hibernate.Hibernate;

public class JsonLazyFilter {
    @Override
    public boolean equals(Object obj) {
        return !Hibernate.isInitialized(obj);
    }
}
