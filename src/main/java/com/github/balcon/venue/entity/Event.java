package com.github.balcon.venue.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@FieldNameConstants
@NamedEntityGraph(name = Event.INCLUDE_BANDS, attributeNodes = @NamedAttributeNode("bands"))
public class Event extends AbstractNamedEntity {
    public static final String INCLUDE_BANDS = "Event.bands";

    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_band",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "band_id"))
    private Set<Band> bands = new HashSet<>();

    public void addBand(Band band) {
        bands.add(band);
    }

    public void removeBand(Band band) {
        bands.remove(band);
    }
}
