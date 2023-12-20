package com.github.balcon.venue.integration;

import com.github.balcon.venue.TestData;
import com.github.balcon.venue.web.rest.EventRestController;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerTest extends BaseIntegrationTest {

    public EventControllerTest() {
        super(EventRestController.URI, TestData.EVENT_ID,
                TestData.EVENT_WRITE_DTO, TestData.EVENT_WRITE_DTO_DUMMY);
    }

    @Test
    void getByDate_responseJsonList_okStatus() throws Exception {
        mockMvc.perform(get(EventRestController.URI + "?date=2000-01-01"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void addBand_success_createdStatus() throws Exception {
        mockMvc.perform(put(EventRestController.URI + "/" + TestData.EVENT_ID
                        + "/bands/" + TestData.BAND_ID))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void removeBand_success_noContentStatus() throws Exception {
        mockMvc.perform(delete(EventRestController.URI + "/" + TestData.EVENT_ID
                        + "/bands/" + TestData.BAND_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
