package com.github.balcon.venue.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Entity class for venue events.
 *
 * @author Konstantin Balykov
 */

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
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
