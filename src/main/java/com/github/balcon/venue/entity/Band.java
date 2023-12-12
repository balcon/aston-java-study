package com.github.balcon.venue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Entity class for music bands.
 *
 * @author Konstantin Balykov
 */

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
  @ToString.Exclude
  @OneToMany(mappedBy = "band", fetch = FetchType.LAZY)
  private List<Musician> musicians = new ArrayList<>();
}
