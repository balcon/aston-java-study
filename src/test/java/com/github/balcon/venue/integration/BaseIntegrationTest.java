package com.github.balcon.venue.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.github.balcon.venue.TestData.DUMMY_ID;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
@Sql("classpath:data.sql")
@RequiredArgsConstructor
abstract class BaseIntegrationTest {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper mapper;

    private final String resourceUri;
    private final int resourceId;
    private final Object validWriteDto;
    private final Object invalidWriteDto;

    @Test
    void getResource_responseJson_okStatus() throws Exception {
        mockMvc.perform(get(resourceUri + "/" + resourceId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(resourceId));
    }

    @Test
    void getResource_notExists_notFoundStatus() throws Exception {
        mockMvc.perform(get(resourceUri + "/" + DUMMY_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllResources_responseJsonList_okStatus() throws Exception {
        mockMvc.perform(get(resourceUri))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void createResource_responseJsonWithId_createdStatus() throws Exception {
        mockMvc.perform(post(resourceUri)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validWriteDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    void createResource_validationError_badRequestStatus() throws Exception {
        mockMvc.perform(post(resourceUri)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidWriteDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateResource_success_noContentStatus() throws Exception {
        mockMvc.perform(put(resourceUri + "/" + resourceId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validWriteDto)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void updateResource_notExists_notFoundStatus() throws Exception {
        mockMvc.perform(put(resourceUri + "/" + DUMMY_ID)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validWriteDto)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void updateResource_validationError_badRequestStatus() throws Exception {
        mockMvc.perform(put(resourceUri + "/" + resourceId)
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(invalidWriteDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_success_noContentStatus() throws Exception {
        mockMvc.perform(delete(resourceUri + "/" + resourceId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_notExists_notFoundStatus() throws Exception {
        mockMvc.perform(delete(resourceUri + "/" + DUMMY_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
