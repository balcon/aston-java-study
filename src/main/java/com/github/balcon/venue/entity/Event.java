package com.github.balcon.venue.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@FieldNameConstants
public class Event extends AbstractNamedEntity {
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "event_band",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "band_id"))
    private List<Band> bands = new ArrayList<>();

    public void addBand(Band band) {
        bands.add(band);
    }

    public void removeBand(Band band) {
        bands.remove(band);
    }
}
