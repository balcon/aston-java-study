package com.github.balcon;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

class QuickSorterTest {

    @Test
    void sortIntegerAsc() {
        Integer[] array = {-29, -37, 10, 28, 5, 45, -14, -33, 46, 16};
        new QuickSorter<Integer>().sort(array, Comparator.naturalOrder());

        assertThat(array).isSortedAccordingTo(Comparator.naturalOrder());
    }

    @Test
    void sortIntegerDesc() {
        Integer[] array = {-29, -37, 10, 28, 5, 45, -14, -33, 46, 16};
        new QuickSorter<Integer>().sort(array, Comparator.reverseOrder());

        assertThat(array).isSortedAccordingTo(Comparator.reverseOrder());
    }

    @Test
    void sortStringAsc() {
        String[] array = {"q", "h", "k", "a"};

        new QuickSorter<String>().sort(array, Comparator.naturalOrder());
        assertThat(array).isSorted();
    }
}
