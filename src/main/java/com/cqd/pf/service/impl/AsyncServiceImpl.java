package com.cqd.pf.service.impl;

import com.cqd.pf.repository.TaskDAO;
import com.cqd.pf.service.AsyncJob;
import com.cqd.pf.service.AsyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//todo объединить с taskservise?
public class AsyncServiceImpl implements AsyncService {

    private final ThreadPoolTaskExecutor asyncExecutor;

    private final TaskDAO taskDAO;

    @Override
    public String postAsync(AsyncJob asyncJob, Object params) {
        String taskId = taskDAO.createIdle();
        asyncExecutor.execute(() -> asyncJob.start(taskId, params));
        return taskId;
    }
}
