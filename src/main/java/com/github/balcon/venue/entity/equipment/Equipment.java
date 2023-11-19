package com.github.balcon.venue.entity.equipment;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Equipment {
    @Id
    @SequenceGenerator(name = "equipment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_seq")
    private Integer id;

    private String name;

    private String manufacturer;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Equipment entity) {
            return Objects.equals(id, entity.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();

    }
}
