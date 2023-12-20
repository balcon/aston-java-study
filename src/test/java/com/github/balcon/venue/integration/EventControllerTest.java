package com.github.balcon.venue.integration;

import com.github.balcon.venue.TestData;
import com.github.balcon.venue.web.rest.EventRestController;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EventControllerTest extends BaseIntegrationTest {

    public EventControllerTest() {
        super(EventRestController.URI, TestData.EVENT_ID,
                TestData.EVENT_WRITE_DTO, TestData.EVENT_WRITE_DTO_DUMMY);
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
