package com.github.balcon.venue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Band extends AbstractNamedEntity {
    @Builder.Default
    @OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
    private List<Musician> musicians = new ArrayList<>();

    public void addMusician(Musician musician) {
        musicians.add(musician);
    }
}
