package com.cqd.pf.service;

import com.cqd.pf.document.Task;
import com.cqd.pf.model.TaskRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskService {

    String postTask(TaskRequest taskRequest);

    Task getById(String id);

    List<Task> getTasks(Pageable pageable);
}
