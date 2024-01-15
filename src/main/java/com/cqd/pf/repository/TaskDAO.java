package com.cqd.pf.repository;

import com.cqd.pf.document.Task;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.utils.MatchResult;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskDAO {

    void saveProgress(String id, Integer progress);

    void saveResult(String jobId, MatchResult result);

    void saveError(String jobId, TaskRequest taskRequest);

    String createIdle();

    Task getById(String id);

    List<Task> getTasks(Pageable original);

}
