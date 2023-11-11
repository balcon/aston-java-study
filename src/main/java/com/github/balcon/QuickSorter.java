package com.github.balcon;

import java.util.Comparator;

public class QuickSorter<E> implements Sorter<E> {
    @Override
    public void sort(E[] array, Comparator<? super E> comparator) {
        sort(array, 0, array.length - 1, comparator);
    }

    @Override
    public void sort(E[] array, int first, int last, Comparator<? super E> comparator) {
        if (first < last) {
            int right = subSort(array, first, last, comparator);
            sort(array, first, right - 1, comparator);
            sort(array, right, last, comparator);
        }
    }

    private int subSort(E[] array, int left, int right, Comparator<? super E> comparator) {
        E pivot = array[(left + right) >> 1];
        while (left <= right) {
            while (comparator.compare(array[left], pivot) < 0) left++;
            while (comparator.compare(array[right], pivot) > 0) right--;
            if (left <= right) {
                E temp = array[left];
                array[left] = array[right];
                array[right] = temp;
                left++;
                right--;
            }
        }
        return left;
    }
}
