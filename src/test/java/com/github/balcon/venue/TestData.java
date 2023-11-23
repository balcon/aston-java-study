package com.github.balcon.venue;

import com.github.balcon.venue.dto.BandDto;
import com.github.balcon.venue.dto.MusicianReadDto;

public class TestData {
    public static final int DUMMY_ID = 0;
    public static final int BAND_ID = 100;
    public static final int MUSICIAN_ID = 120;

    public static final BandDto BAND = BandDto.builder()
            .id(100)
            .name("Band Number One").build();

    public static final MusicianReadDto MUSICIAN = MusicianReadDto.builder()
            .id(120)
            .name("Musician #120")
            .role("Singer")
            .band(BAND).build();

    private TestData() {
    }
}
