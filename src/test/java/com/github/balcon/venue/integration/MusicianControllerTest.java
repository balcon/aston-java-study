package com.github.balcon.venue.integration;

import com.github.balcon.venue.TestData;
import com.github.balcon.venue.web.rest.MusicianRestController;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MusicianControllerTest extends BaseIntegrationTest {

    public MusicianControllerTest() {
        super(MusicianRestController.URI, TestData.MUSICIAN_ID,
                TestData.MUSICIAN_WRITE_DTO, TestData.MUSICIAN_WRITE_DTO_DUMMY);
    }

    @Test
    void getByName_responseJsonList_okStatus() throws Exception {
        mockMvc.perform(get(MusicianRestController.URI+"?name=John"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
