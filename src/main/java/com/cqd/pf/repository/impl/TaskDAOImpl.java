package com.cqd.pf.repository.impl;

import com.cqd.pf.document.Task;
import com.cqd.pf.errorhandling.exception.ServiceException;
import com.cqd.pf.errorhandling.message.Message;
import com.cqd.pf.repository.TaskRepository;
import com.cqd.pf.repository.TaskDAO;
import com.cqd.pf.utils.MatcherResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDAOImpl implements TaskDAO {

    private static final Sort DEFAULT_SORT = Sort.by("id").ascending();

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

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
    public void setProgress(String id, Integer progress) {
        Task task = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(id)), Task.class);
        task.setProgress(progress);

        taskRepository.save(task);
    }

    @Override
    public void setResult(String jobId, MatcherResult result) {
        Task task = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(jobId)), Task.class);
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
