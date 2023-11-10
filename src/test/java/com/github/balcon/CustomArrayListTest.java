package com.github.balcon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIndexOutOfBoundsException;

class CustomArrayListTest {
    CustomArrayList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
        list.add(10);
        list.add(30);
        list.add(20);
        list.add(50);
    }

    @Test
    void add() {
        list.add(0);

        assertThat(list.size()).isEqualTo(4 + 1);
        assertThat(list.get(4)).isEqualTo(0);
    }

    @Test
    void addToIndex() {
        list.add(2, 0);

        System.out.println(list);
        assertThat(list.size()).isEqualTo(4 + 1);
        assertThat(list.get(2)).isEqualTo(0);
        assertThat(list.get(3)).isEqualTo(20);
    }

    @Test
    void addOutOfBound() {
        assertThatIndexOutOfBoundsException().isThrownBy(() -> list.add(9, 0));
    }

    @Test
    void addAll() {
        list.addAll(List.of(1, 2, 3));

        assertThat(list.size()).isEqualTo(4 + 3);
        assertThat(list.get(5)).isEqualTo(2);
    }

    @Test
    void get() {
        assertThat(list.get(2)).isEqualTo(20).isInstanceOf(Integer.class);
    }

    @Test
    void getOutOfBound() {
        assertThatIndexOutOfBoundsException().isThrownBy(() -> list.get(9));
    }

    @Test
    void removeById() {
        Integer removed = list.remove(1);

        assertThat(list.size()).isEqualTo(4 - 1);
        assertThat(list.get(1)).isEqualTo(20);
        assertThat(removed).isEqualTo(30);
    }

    @Test
    void removeOutOfBound() {
        assertThatIndexOutOfBoundsException().isThrownBy(() -> list.remove(9));
    }

    @Test
    void removeByObjectSuccess() {
        boolean result = list.remove(Integer.valueOf(10));

        assertThat(result).isTrue();
        assertThat(list.get(0)).isEqualTo(30);
        assertThat(list.size()).isEqualTo(4 - 1);
    }

    @Test
    void removeByObjectFail() {
        boolean result = list.remove(Integer.valueOf(0));

        assertThat(result).isFalse();
        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    void size() {
        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    void clear() {
        list.clear();

        assertThat(list.size()).isEqualTo(0);
        assertThat(list.capacity()).isEqualTo(10);
    }

    @Test
    void isEmpty() {
        assertThat(list.isEmpty()).isFalse();
        assertThat(new CustomArrayList<>().isEmpty()).isTrue();
    }

    @Test
    void trim() {
        list.trimToSize();

        assertThat(list.capacity()).isEqualTo(list.size());
    }

    @Test
    void addWithCapacityIncrease() {
        for (int i = 0; i < 7; i++) {
            list.add(i);
        }

        assertThat(list.size()).isEqualTo(11);
        assertThat(list.capacity()).isEqualTo(15);
    }

    @Test
    void addToIndexWithCapacityIncrease() {
        for (int i = 0; i < 7; i++) {
            list.add(3, i);
        }

        assertThat(list.size()).isEqualTo(11);
        assertThat(list.capacity()).isEqualTo(15);
    }

    @Test
    void addAllWithCapacityIncrease() {
        list.addAll(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));

        assertThat(list.size()).isEqualTo(13);
        assertThat(list.capacity()).isEqualTo(15);
    }

    @Test
    void equalsTrue() {
        CustomList<Integer> otherList = new CustomArrayList<>();
        otherList.add(10);
        otherList.add(30);
        otherList.add(20);
        otherList.add(50);

        assertThat(list.equals(otherList)).isTrue();
    }

    @Test
    void equalsFalse() {
        CustomList<Integer> otherList = new CustomArrayList<>();
        otherList.add(10);
        otherList.add(20);
        otherList.add(30);
        otherList.add(50);

        assertThat(list.equals(otherList)).isFalse();
    }

    @Test
    void equalHashCodes() {
        CustomList<Integer> otherList = new CustomArrayList<>();
        otherList.add(10);
        otherList.add(30);
        otherList.add(20);
        otherList.add(50);

        assertThat(list.hashCode()).isEqualTo(otherList.hashCode());
    }

    @Test
    void notEqualHashCodes() {
        CustomList<Integer> otherList = new CustomArrayList<>();
        otherList.add(10);
        otherList.add(20);
        otherList.add(30);
        otherList.add(50);

        assertThat(list.hashCode()).isNotEqualTo(otherList.hashCode());
    }

    @Test
    void sort() {
    }
}
