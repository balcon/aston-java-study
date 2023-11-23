package com.github.balcon.venue.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@NamedEntityGraph(name = Band.INCLUDE_MUSICIANS, attributeNodes = @NamedAttributeNode("musicians"))
public class Band extends AbstractNamedEntity {
    public static final String INCLUDE_MUSICIANS = "Band.musicians";

    @Builder.Default
    @OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
    private List<Musician> musicians = new ArrayList<>();
}
