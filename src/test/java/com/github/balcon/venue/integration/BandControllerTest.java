package com.github.balcon.venue.integration;

import com.github.balcon.venue.TestData;
import com.github.balcon.venue.web.rest.BandRestController;

class BandControllerTest extends BaseIntegrationTest {

    public BandControllerTest() {
        super(BandRestController.URI, TestData.BAND_ID,
                TestData.BAND_WRITE_DTO, TestData.BAND_WRITE_DTO_DUMMY);
    }
}
