package com.dreamworks.example.service.impl;

import com.dreamworks.example.model.Task;
import com.dreamworks.example.model.TaskVersion;
import com.dreamworks.example.model.request.TaskPayload;
import com.dreamworks.example.model.request.TaskVersionPayload;
import com.dreamworks.example.repository.TaskRepository;
import com.dreamworks.example.repository.TaskVersionRepository;
import com.dreamworks.example.service.TaskService;
import com.dreamworks.example.support.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by mmonti on 6/23/17.
 */
@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;
    private TaskVersionRepository taskVersionRepository;
    private IDGenerator idGenerator;

    @Autowired
    public TaskServiceImpl(final TaskRepository taskRepository,
                           final TaskVersionRepository taskVersionRepository,
                           final IDGenerator idGenerator) {

        this.taskRepository = taskRepository;
        this.taskVersionRepository = taskVersionRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public Task get(final String taskId) {
        final Task task = taskRepository.findById(taskId);
        if (task == null) {
            log.debug("Task with id=[{}] not found", taskId);
            throw new NotFoundException("Task with id=["+ taskId +"] not found");
        }
        return task;
    }

    @Override
    @Transactional
    public Task create(final TaskPayload payload) {
        final String id = idGenerator.postfixId(Task.class, x->x.uuid());
        return taskRepository.save(new Task(id, payload.getUti()));
    }

    @Override
    @Transactional
    public TaskVersion create(final String taskId, final String taskVersionId, final TaskVersionPayload payload) {
        final Task task = get(taskId);

        TaskVersion taskVersion;
        if (taskVersionId == null) {
            taskVersion = new TaskVersion(idGenerator.postfixId(TaskVersion.class, x->x.uuid()), task);
        } else {
            final TaskVersion previous = taskVersionRepository.findById(taskVersionId);
            taskVersion = new TaskVersion(idGenerator.postfixId(TaskVersion.class, x->x.uuid()), task, previous);
        }
        return taskVersionRepository.save(taskVersion, 2);
    }

}
