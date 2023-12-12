package com.github.balcon.venue;

import com.github.balcon.venue.dto.BandDto;

public final class TestData {
    public static final int DUMMY_ID = 0;
    public static final int BAND_ID = 100;
    public static final int MUSICIAN_ID = 120;
    public static final int EVENT_ID = 110;

    public static final BandDto BAND = BandDto.builder()
            .id(100)
            .name("Band Number One").build();

    private TestData() {
    }
}
