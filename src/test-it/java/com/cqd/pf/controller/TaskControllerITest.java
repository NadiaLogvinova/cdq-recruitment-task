package com.cqd.pf.controller;

import com.cqd.pf.BaseIT;
import com.cqd.pf.repository.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerITest extends BaseIT {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void cleanUp() {
        this.taskRepository.deleteAll();
    }

    @Test
    void test_notFound() throws Exception {
        mockMvc.perform(get("/tasks/1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Timeout(15)
    void test_getTaskById() throws Exception {
        MvcResult postResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"input\": \"string\", \"pattern\": \"string\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String id = postResult.getResponse().getContentAsString();

        MvcResult getResult;
        do {
            getResult = mockMvc.perform(get("/tasks/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } while (!getResult.getResponse().getContentAsString().contains("\"progress\":100"));

        // clear DB.
        cleanUp();

        MvcResult getResultFromCache = mockMvc.perform(get("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(getResult.getResponse().getContentAsString(), getResultFromCache.getResponse().getContentAsString());
    }
}
