package com.github.balcon.venue.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public abstract class AbstractNamedEntity {
    @Id
    @SequenceGenerator(name = "main_seq", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "main_seq")
    private Integer id;

    private String name;

    public boolean hasId() {
        return id != null;
    }
}
