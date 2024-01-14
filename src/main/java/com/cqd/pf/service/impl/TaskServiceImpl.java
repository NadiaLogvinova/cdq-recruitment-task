package com.cqd.pf.service.impl;

import com.cqd.pf.document.Task;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.repository.TaskDAO;
import com.cqd.pf.service.TaskService;
import com.cqd.pf.utils.MatchResult;
import com.cqd.pf.utils.MatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final MatherService matherService;

    private final TaskDAO taskDAO;

    private final ThreadPoolTaskExecutor asyncExecutor;

    private final ThreadLocal<String> jobId = new ThreadLocal<>();

    @Override
    public String postTask(TaskRequest taskRequest) {
        String taskId = taskDAO.createIdle();
        asyncExecutor.execute(() -> start(taskId, taskRequest));
        return taskId;
    }

    @Override
    public Task getById(String id) {
        return taskDAO.getById(id);
    }

    @Override
    public List<Task> getTasks(Pageable pageable) {
        return taskDAO.getTasks(pageable);
    }

    private void start(String id, TaskRequest taskRequest) {
        jobId.set(id);
        MatchResult bestMatch = matherService.findBestMatch(taskRequest, this::setProgress);
        setResult(bestMatch);
        jobId.remove();
    }

    private void setProgress(Integer progress) {
        taskDAO.setProgress(jobId.get(), progress);
    }

    private void setResult(MatchResult result) {
        taskDAO.setResult(jobId.get(), result);
    }
}
