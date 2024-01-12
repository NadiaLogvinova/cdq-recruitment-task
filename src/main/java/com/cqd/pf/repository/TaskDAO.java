package com.cqd.pf.repository;

import com.cqd.pf.document.Task;
import com.cqd.pf.utils.MatcherResult;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TaskDAO {

    void setProgress(String id, Integer progress);

    void setResult(String jobId, MatcherResult result);

    String createIdle();

    Task getById(String id);

    List<Task> getTasks(Pageable original);

}
