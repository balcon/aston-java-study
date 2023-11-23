package com.github.balcon.venue.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@NamedEntityGraph(name = Musician.INCLUDE_BAND, attributeNodes = @NamedAttributeNode("band"))
public class Musician extends AbstractNamedEntity {
    public static final String INCLUDE_BAND = "Musician.band";

    private String role;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Band band;
}
