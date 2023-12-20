package com.github.balcon.venue.integration;

import com.github.balcon.venue.TestData;
import com.github.balcon.venue.web.rest.MusicianRestController;

class MusicianControllerTest extends BaseIntegrationTest {

    public MusicianControllerTest() {
        super(MusicianRestController.URI, TestData.MUSICIAN_ID,
                TestData.MUSICIAN_WRITE_DTO, TestData.MUSICIAN_WRITE_DTO_DUMMY);
    }
}
