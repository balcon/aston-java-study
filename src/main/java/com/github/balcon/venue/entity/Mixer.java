package com.github.balcon.venue.entity;

import jakarta.persistence.Column;
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
public class Mixer extends Equipment {
    @Column(name = "mic_channels")
    private int micChannels;

    @Column(name = "stereo_channels")
    private int stereoChannels;
}
