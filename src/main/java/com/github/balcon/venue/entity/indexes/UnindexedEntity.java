package com.github.balcon.venue.entity.indexes;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "unindexed_entity")
public class UnindexedEntity extends BaseEntity {
    private int random;

    public UnindexedEntity() {
        this.random = RANDOM.nextInt(MAX_RANDOM);
    }
}
