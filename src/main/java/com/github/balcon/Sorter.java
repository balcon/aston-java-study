package com.github.balcon;

import java.util.Comparator;

public interface Sorter<E> {
    void sort(E[] array, Comparator<? super E> comparator);

    void sort(E[] array, int first, int last, Comparator<? super E> comparator);
}
