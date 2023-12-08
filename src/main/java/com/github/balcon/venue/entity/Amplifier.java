package com.github.balcon.venue.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Entity class for amplifiers.
 *
 * @author Konstantin Balykov
 */

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@PrimaryKeyJoinColumn(name = "equipment_id")
public class Amplifier extends Equipment {
  private int power;

  private int channels;
}
