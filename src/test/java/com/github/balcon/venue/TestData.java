package com.github.balcon.venue;

import com.github.balcon.venue.dto.BandWriteDto;
import com.github.balcon.venue.dto.EventWriteDto;
import com.github.balcon.venue.dto.MusicianWriteDto;
import com.github.balcon.venue.entity.Band;
import com.github.balcon.venue.entity.Event;
import com.github.balcon.venue.entity.Musician;

import java.time.LocalDateTime;

public final class TestData {
    public static final int DUMMY_ID = 0;
    public static final int BAND_ID = 100;
    public static final int MUSICIAN_ID = 120;
    public static final int EVENT_ID = 110;

    public static final Band BAND_DUMMY = Band.builder().build();
    public static final BandWriteDto BAND_WRITE_DTO_DUMMY = BandWriteDto.builder().build();
    public static final BandWriteDto BAND_WRITE_DTO =
            BandWriteDto.builder()
                    .name("Name").build();

    public final static Event EVENT_DUMMY = Event.builder().build();
    public final static EventWriteDto EVENT_WRITE_DTO_DUMMY = EventWriteDto.builder().build();
    public static final EventWriteDto EVENT_WRITE_DTO =
            EventWriteDto.builder()
                    .name("Name")
                    .dateTime(LocalDateTime.now()).build();

    public final static Musician MUSICIAN_DUMMY = Musician.builder().build();
    public final static MusicianWriteDto MUSICIAN_WRITE_DTO_DUMMY = MusicianWriteDto.builder().build();
    public static final MusicianWriteDto MUSICIAN_WRITE_DTO =
            MusicianWriteDto.builder()
                    .name("Name")
                    .role("Role")
                    .bandId(BAND_ID).build();

    private TestData() {
    }
}
