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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerITest extends BaseIT {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void cleanUp() {
        this.taskRepository.deleteAll();
    }

    @Test
    void postTask_isCreated_returnId() throws Exception {
        MvcResult postResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"input\": \"string\", \"pattern\": \"string\"}"))
                .andExpect(status().isCreated())
                .andReturn();
        String id = postResult.getResponse().getContentAsString();

        assertNotNull(id);
    }

    @Test
    void getTaskById_notFound() throws Exception {
        mockMvc.perform(get("/tasks/1111")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @Timeout(15)
    void getTaskById_isOk_readFromDbAndCache() throws Exception {
        MvcResult postResult = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"input\": \"string\", \"pattern\": \"string\"}"))
                .andReturn();
        String id = postResult.getResponse().getContentAsString();

        MvcResult getResultFromDb;
        do {
            getResultFromDb = mockMvc.perform(get("/tasks/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();
        } while (!getResultFromDb.getResponse().getContentAsString().contains("\"progress\":100"));

        // clear DB.
        cleanUp();

        MvcResult getResultFromCache = mockMvc.perform(get("/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(getResultFromDb.getResponse().getContentAsString(), getResultFromCache.getResponse().getContentAsString());
    }

    @Test
    void getTasks_isOk() throws Exception {

        int page = 0;
        int pageSize = 5;

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"input\": \"string1\", \"pattern\": \"string\"}"));

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"input\": \"string2\", \"pattern\": \"string\"}"))
                .andReturn();

        mockMvc
                .perform(
                        get("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(pageSize))
                )
                .andExpect(status().isOk());
    }
}
