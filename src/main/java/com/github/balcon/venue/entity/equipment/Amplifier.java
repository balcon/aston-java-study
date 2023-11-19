package com.github.balcon.venue.entity.equipment;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
