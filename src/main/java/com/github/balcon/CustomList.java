package com.github.balcon;

import java.util.Collection;
import java.util.Comparator;

public interface CustomList<E> {
    void add(E element);

    void add(int index, E element);

    void addAll(Collection<? extends E> collection);

    E get(int index);

    E remove(int index);

    boolean remove(Object o);

    void clear();

    boolean isEmpty();

    int size();

    void sort(Comparator<? super E> comparator);
}
