package com.cqd.pf.controller;

import com.cqd.pf.document.Task;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.service.AsyncService;
import com.cqd.pf.service.TaskService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TaskController {

    private AsyncService asyncService;

    private TaskService taskService;

    @PostMapping
    public String createTask(@RequestBody TaskRequest taskRequest) {
        return asyncService.postTask(taskService, taskRequest);
    }

    @GetMapping
    // todo: create DTO
    public List<Task> getAllTasks(Pageable pageable) {
        return taskService.getTasks(pageable);
    }

    @GetMapping("/{id}")
    public Task getTaskInfo(@PathVariable String id) {
        return taskService.getById(id);
    }
}
