package com.cqd.pf.service;

import com.cqd.pf.document.Task;
import com.cqd.pf.errorhandling.exception.ServiceException;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.repository.TaskDAO;
import com.cqd.pf.utils.MatcherResult;
import com.cqd.pf.utils.MatherUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskService implements AsyncJob{

    private final MatherUtils matherUtils;

    private final TaskDAO taskDAO;

    private ThreadLocal<String> jobId = new ThreadLocal<>();

    public MatcherResult findBestMatch(TaskRequest taskRequest) {
        String input = taskRequest.getInput();
        String pattern = taskRequest.getPattern();

        int position = 0;
        int minTypos = pattern.length();

        for (int i = 0; i <= input.length() - pattern.length(); i++) {
            setProgress(100 * i/(input.length() - pattern.length() + 1));
            int typos = matherUtils.getTypos(input.substring(i, i + pattern.length()), pattern);
            minTypos = Math.min(minTypos, typos);
            if (minTypos == 0) {
                new MatcherResult(position, typos);
            }
        }
        return new MatcherResult(position, minTypos);
    }

    public Task getById(String id) {
        return taskDAO.getById(id);
    }

    public List<Task> getTasks(Pageable pageable) {
        return taskDAO.getTasks(pageable);
    }

    @Override
    public void start(String id, Object params) {
        if (params instanceof TaskRequest taskRequest) {
            jobId.set(id);
            MatcherResult bestMatch = findBestMatch(taskRequest);
            setResult(bestMatch);
        } else {
            throw new ServiceException("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void setProgress(Integer progress) {
        taskDAO.setProgress(jobId.get(), progress);
    }

    private void setResult(MatcherResult result) {
        taskDAO.setResult(jobId.get(), result);
    }

}
