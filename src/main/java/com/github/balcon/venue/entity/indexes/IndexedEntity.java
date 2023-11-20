package com.github.balcon.venue.entity.indexes;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "indexed_entity", indexes = @Index(name = "entity_idx", columnList = "random"))
public class IndexedEntity extends BaseEntity {
    private int random;

    public IndexedEntity() {
        this.random = RANDOM.nextInt(MAX_RANDOM);
    }
}
