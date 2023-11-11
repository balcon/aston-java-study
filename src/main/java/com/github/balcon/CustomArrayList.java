package com.github.balcon;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class CustomArrayList<E> implements CustomList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size = 0;
    private final Sorter<E> sorter;

    public CustomArrayList() {
        array = new Object[DEFAULT_CAPACITY];
        sorter = new QuickSorter<>();
    }

    @Override
    public void add(E element) {
        checkCapacity();
        array[size++] = element;
    }

    @Override
    public void add(int index, E element) {
        checkCapacity();
        if (index == size) {
            array[size++] = element;
            return;
        }
        checkOutOfBound(index);
        if (index != size) {
            System.arraycopy(array, index, array, index + 1, size - index);
            array[index] = element;
            size++;
        }
    }

    @Override
    public void addAll(Collection<? extends E> collection) {
        int newSize = size + collection.size();
        if (newSize > capacity()) {
            increaseCapacity(newSize);
        }
        for (E element : collection) {
            array[size++] = element;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        checkOutOfBound(index);
        return (E) array[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        checkOutOfBound(index);
        Object removed = array[index];
        removeElement(index);
        return (E) removed;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                removeElement(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        array = new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super E> comparator) {
        sorter.sort((E[]) array, 0, size - 1, comparator);
    }

    public void trimToSize() {
        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    public int capacity() {
        return array.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomList<?> list)) {
            return false;
        }
        if (size != list.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(array[i], list.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    private void removeElement(int index) {
        System.arraycopy(array, index + 1, array, index, size - index);
        size--;
    }

    private void checkCapacity() {
        if (size == array.length) {
            increaseCapacity();
        }
    }

    private void increaseCapacity() {
        int capacity = array.length;
        int newCapacity = capacity + (capacity >> 1);
        increaseCapacity(newCapacity);
    }

    private void increaseCapacity(int newCapacity) {
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void checkOutOfBound(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index %d out of bounds for size %d".formatted(index, size));
        }
    }
}
