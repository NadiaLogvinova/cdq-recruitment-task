package com.cqd.pf.repository.impl;

import com.cqd.pf.document.Task;
import com.cqd.pf.errorhandling.exception.ServiceException;
import com.cqd.pf.errorhandling.message.Message;
import com.cqd.pf.repository.TaskDAO;
import com.cqd.pf.repository.TaskRepository;
import com.cqd.pf.utils.MatchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
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
    @CacheEvict(cacheNames = "task", key = "#id")
    public void saveProgress(String id, Integer progress) {
        Task task = Task.builder()
                .id(id)
                .progress(progress)
                .build();

        taskRepository.save(task);
    }

    @Override
    @CacheEvict(cacheNames = "task", key = "#id")
    public void saveResult(String id, MatchResult result) {
        Task task = Task.builder()
                .id(id)
                .progress(100)
                .position(result.getPosition())
                .typos(result.getTypo())
                .build();

        taskRepository.save(task);
    }

    @Override
    public String createIdle() {
        Task task = Task.builder().build();
        Task insert = taskRepository.insert(task);
        return insert.getId();
    }
}
