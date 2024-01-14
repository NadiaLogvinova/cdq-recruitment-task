package com.cqd.pf.repository.impl;

import com.cqd.pf.document.Task;
import com.cqd.pf.errorhandling.exception.ServiceException;
import com.cqd.pf.errorhandling.message.Message;
import com.cqd.pf.repository.TaskDAO;
import com.cqd.pf.repository.TaskRepository;
import com.cqd.pf.utils.MatchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskDAOImpl implements TaskDAO {

    private static final Sort DEFAULT_SORT = Sort.by("id").ascending();

    private final TaskRepository taskRepository;

    @Override
    public Task getById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format(Message.TASK_DOES_NOT_EXIST, id), HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Task> getTasks(Pageable original) {
        Pageable sortedPageable = PageRequest.of(original.getPageNumber(), original.getPageSize(), DEFAULT_SORT);
        return taskRepository.findAll(sortedPageable).stream().toList();
    }

    @Override
    //todo update progress
    public void setProgress(String id, Integer progress) {
        Task task = taskRepository.findById(id).get();
        task.setProgress(progress);

        taskRepository.save(task);
    }

    @Override
    //todo why jobId here?
    public void setResult(String jobId, MatchResult result) {
        Task task = taskRepository.findById(jobId).get();
        task.setProgress(100);
        task.setPosition(result.getPosition());
        task.setTypos(result.getTypo());

        taskRepository.save(task);
    }

    @Override
    public String createIdle() {
        Task task = Task.builder().build();
        Task insert = taskRepository.insert(task);
        return insert.getId();
    }
}
