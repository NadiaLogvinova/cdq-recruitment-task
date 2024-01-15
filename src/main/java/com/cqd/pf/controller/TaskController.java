package com.cqd.pf.controller;

import com.cqd.pf.document.Task;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Cacheable(value = "tasks_id")
    public String createTask(@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.postTask(taskRequest);
    }

    @GetMapping
    public List<Task> getAllTasks(Pageable pageable) {
        return taskService.getTasks(pageable);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "tasks")
    public Task getTaskInfo(@PathVariable String id) {
        return taskService.getById(id);
    }
}
