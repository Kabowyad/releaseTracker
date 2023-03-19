package org.neon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.neon.AbstractBaseTest;
import org.neon.entity.Release;
import org.neon.entity.enums.ReleaseStatus;
import org.neon.models.request.CreateReleaseRequest;
import org.neon.models.request.UpdateReleaseRequest;
import org.neon.repository.ReleaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReleaseControllerTest extends AbstractBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReleaseRepository repository;

    @BeforeEach
    public void setUp() {

        repository.deleteAll();
    }

    @Test
    @DisplayName("POST /releases - Success")
    public void testCreateRelease() throws Exception {

        CreateReleaseRequest request =
                new CreateReleaseRequest("New Release", "A new release is created",
                        ReleaseStatus.DONE, LocalDate.of(2023, 4, 1));

        mockMvc.perform(post("/releases").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Release"))
                .andExpect(jsonPath("$.description").value("A new release is created"));

        Assertions.assertEquals(1, repository.findAll().size());
    }

    @Test
    @DisplayName("GET /releases - Success")
    public void testGetReleaseById() throws Exception {

        Release release = new Release("Test Release", "Test Description", ReleaseStatus.DONE,
                LocalDate.now());
        Release saved = repository.save(release);

        mockMvc.perform(get("/releases/{id}", saved.getId())).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(saved.getId()))
                .andExpect(jsonPath("$.name").value("Test Release"))
                .andExpect(jsonPath("$.description").value("Test Description"))
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    @DisplayName("GET /releases - Exception")
    public void testGetReleaseByIdThrowException() throws Exception {

        mockMvc.perform(get("/releases/{id}", 1)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /releases - Success")
    void testDeleteRelease() throws Exception {

        Release release = new Release("Test Release", "Test Description", ReleaseStatus.DONE,
                LocalDate.now());
        Release saved = repository.save(release);
        Assertions.assertEquals(1, repository.findAll().size());

        mockMvc.perform(delete("/releases/{id}", saved.getId())).andExpect(status().isOk());

        Assertions.assertEquals(0, repository.findAll().size());

    }

    @Test
    @DisplayName("PUT /releases - Success update name")
    public void testUpdateRelease() throws Exception {
        Release release = new Release("Test Release", "Test Description", ReleaseStatus.DONE,
                LocalDate.now());
        Release saved = repository.save(release);

        UpdateReleaseRequest request = new UpdateReleaseRequest("New Test Release", null, null);

        // Perform PUT request
        mockMvc.perform(put("/releases/{id}", saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Test Release"));

    }

    @Test
    @DisplayName("PUT /releases - Exception no id found")
    void testUpdateReleaseWithInvalidId() throws Exception {

        UpdateReleaseRequest request = new UpdateReleaseRequest("New Test Release", null, null);

        // Perform PUT request
        mockMvc.perform(put("/releases/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

}
