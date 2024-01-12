package com.cqd.pf.service.impl;

import com.cqd.pf.service.AsyncJob;
import com.cqd.pf.service.AsyncService;
import com.cqd.pf.repository.TaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class AsyncServiceImpl implements AsyncService {

    @Autowired
    private ThreadPoolTaskExecutor asyncExecutor;

    @Autowired
    private TaskDAO taskDAO;

    @Override
    public String postTask(AsyncJob asyncJob, Object params) {
        String taskId = taskDAO.createIdle();
        asyncExecutor.execute(() -> asyncJob.start(taskId, params));
        return taskId;
    }
}
