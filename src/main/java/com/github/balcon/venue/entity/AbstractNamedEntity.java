package com.github.balcon.venue.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Base class for project entities.
 *
 * @author Konstantin Balykov
 */

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode(of = "id")
public abstract class AbstractNamedEntity {
  public static final int START_SEQ = 1000;

  @Id
  @SequenceGenerator(name = "main_seq", allocationSize = 1,
          initialValue = START_SEQ)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "main_seq")
  private Integer id;

  private String name;

  public boolean hasId() {
    return id != null;
  }
}
