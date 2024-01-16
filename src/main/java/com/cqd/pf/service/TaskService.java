package com.cqd.pf.service;

import com.cqd.pf.document.Task;
import com.cqd.pf.model.Pageable;
import com.cqd.pf.model.TaskRequest;

import java.util.List;

public interface TaskService {

    String postTask(TaskRequest taskRequest);

    Task getById(String id);

    List<Task> getTasks(Pageable page);
}
