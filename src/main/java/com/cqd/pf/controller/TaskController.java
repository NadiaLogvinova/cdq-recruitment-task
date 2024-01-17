package com.cqd.pf.controller;

import com.cqd.pf.config.CacheConfig;
import com.cqd.pf.document.Task;
import com.cqd.pf.model.Pageable;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Mather finder project API", description = "API to allow you create tasks for string matching and get results")
@RequestMapping(value = "/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Cacheable(value = "tasks_id", key = CacheConfig.TASK_REQUEST_CACHE_KEY_PATTERN)
    @Operation(summary = "Create task", description = "Create a task and return its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Bad request - Check, input and pattern are not empty and input longer then pattern")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public String createTask(@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.postTask(taskRequest);
    }

    @GetMapping
    @Operation(summary = "Get tasks", description = "Get tasks by page and page size. Default values: page = 0, size = 10")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved")
    public List<Task> getAllTasks(@Valid Pageable page) {
        return taskService.getTasks(page);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "tasks", key = CacheConfig.TASK_CACHE_KEY_PATTERN)
    @Operation(summary = "Get a task by id", description = "Returns a task by the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The task was not found")
    })
    public Task getTaskInfo(@PathVariable String id) {
        return taskService.getById(id);
    }
}
