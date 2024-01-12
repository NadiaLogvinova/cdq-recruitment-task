package com.cqd.pf.service;

import com.cqd.pf.document.Task;
import com.cqd.pf.errorhandling.exception.ServiceException;
import com.cqd.pf.errorhandling.message.Message;
import com.cqd.pf.model.TaskRequest;
import com.cqd.pf.repository.TaskRepository;
import com.cqd.pf.utils.MatcherResult;
import com.cqd.pf.utils.MatherUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TaskService {

    private static final Sort DEFAULT_SORT = Sort.by("id").ascending();

    private MatherUtils matherUtils;

    private TaskRepository taskRepository;

    public String findBestMatch(TaskRequest taskRequest) {
        MatcherResult matcherResult = matherUtils.findFirstBestMath(taskRequest.getInput(), taskRequest.getPattern());
        Task task = Task.builder()
                .position(matcherResult.getPosition())
                .typos(matcherResult.getTypo())
                .build();
        taskRepository.save(task);

        return task.getId();
    }

    public Task getById(String id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ServiceException(String.format(Message.TASK_DOES_NOT_EXIST, id), HttpStatus.NOT_FOUND));
    }

    public List<Task> getTasks(Pageable original) {
        Pageable sortedPageable = PageRequest.of(original.getPageNumber(), original.getPageSize(), DEFAULT_SORT);
        return taskRepository.findAll(sortedPageable).stream().toList();
    }
}
