package com.github.balcon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIndexOutOfBoundsException;

class CustomArrayListTest {
    private CustomArrayList<Integer> list;
    private final CustomList<Integer> sameList = new CustomArrayList<>();
    private final CustomList<Integer> otherList = new CustomArrayList<>();

    CustomArrayListTest() {
        sameList.addAll(List.of(0, 10, 20, 30));
        otherList.addAll(List.of(30, 20, 10, 0));
    }

    @BeforeEach
    void setUp() {
        list = new CustomArrayList<>();
        list.add(0);
        list.add(10);
        list.add(20);
        list.add(30);
    }

    @Test
    void add() {
        list.add(40);

        assertThat(list.size()).isEqualTo(4 + 1);
        assertThat(list.get(4)).isEqualTo(40);
    }

    @Test
    void addToIndex() {
        list.add(2, 15);

        assertThat(list.size()).isEqualTo(4 + 1);
        assertThat(list.get(2)).isEqualTo(15);
        assertThat(list.get(3)).isEqualTo(20);
    }

    @Test
    void addOutOfBound() {
        assertThatIndexOutOfBoundsException().isThrownBy(() -> list.add(9, 0));
    }

    @Test
    void addAll() {
        list.addAll(List.of(40, 50, 60));

        assertThat(list.size()).isEqualTo(4 + 3);
        assertThat(list.get(5)).isEqualTo(50);
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
        assertThat(removed).isEqualTo(10);
    }

    @Test
    void removeOutOfBound() {
        assertThatIndexOutOfBoundsException().isThrownBy(() -> list.remove(9));
    }

    @Test
    void removeByObjectSuccess() {
        boolean result = list.remove(Integer.valueOf(10));

        assertThat(result).isTrue();
        assertThat(list.get(1)).isEqualTo(20);
        assertThat(list.size()).isEqualTo(4 - 1);
    }

    @Test
    void removeByObjectFail() {
        boolean result = list.remove(Integer.valueOf(100));

        assertThat(result).isFalse();
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
        List<Integer> longList = IntStream.range(0, 30).boxed().toList();
        list.addAll(longList);

        assertThat(list.size()).isEqualTo(34);
        assertThat(list.capacity()).isEqualTo(34);
    }

    @Test
    void equalsTrue() {
        assertThat(list).isEqualTo(sameList);
    }

    @Test
    void equalsFalse() {
        assertThat(list).isNotEqualTo(otherList);
    }

    @Test
    void equalHashCodes() {
        assertThat(list.hashCode()).isEqualTo(sameList.hashCode());
    }

    @Test
    void notEqualHashCodes() {
        assertThat(list.hashCode()).isNotEqualTo(otherList.hashCode());
    }

    @Test
    void sortDesc() {
        Comparator<Integer> comparator = (a, b) -> Integer.compare(b, a);
        list.sort(comparator);

        assertThat(list).isEqualTo(otherList);
    }
}
