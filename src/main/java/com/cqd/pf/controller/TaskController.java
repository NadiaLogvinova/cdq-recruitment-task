package com.cqd.pf.controller;

import com.cqd.pf.document.Task;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @PostMapping
    public String createTask(@RequestBody TaskRequest taskRequest) {
       return taskService.findBestMatch(taskRequest);
    }

    @GetMapping
    public List<Task> getAllTasks(Pageable pageable) {
        return taskService.getTasks(pageable);
    }

    @GetMapping("/{id}")
    public Task getTaskInfo(@PathVariable String id) {
        return taskService.getById(id);
    }


}
