package com.github.balcon;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class CustomArrayList<E> implements CustomList<E> {
    public static final int DEFAULT_CAPACITY = 10;
    private Object[] array;
    private int size = 0;

    public CustomArrayList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    @Override
    public void add(E element) {
        checkCapacity(size + 1);
        array[size++] = element;
    }

    // TODO: 10.11.2023 Do it better
    @Override
    public void add(int index, E element) {
        checkCapacity(size + 1);
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
        checkCapacity(size + collection.size());
        for (E element : collection) {
            array[size++] = element;
        }
    }

    @Override
    @SuppressWarnings("all")
    public E get(int index) {
        checkOutOfBound(index);
        return (E) array[index];
    }

    @Override
    @SuppressWarnings("all")
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
    public void sort(Comparator<? super E> comparator) {

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

    private void removeElement(int index) {
        System.arraycopy(array, index + 1, array, index, size - index);
        size--;
    }

    private void checkCapacity(int expectedSize) {
        if (expectedSize > array.length) {
            increaseCapacity();
        }
    }

    private void increaseCapacity() {
        int capacity = array.length;
        int newCapacity = capacity + (capacity >> 1);
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
