package com.dreamworks.example.service.impl;

import com.dreamworks.example.model.TaskVersion;
import com.dreamworks.example.repository.TaskVersionRepository;
import com.dreamworks.example.service.TaskVersionService;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mmonti on 4/20/17.
 */
@Slf4j
@Service
public class TaskVersionServiceImpl implements TaskVersionService {

    private TaskVersionRepository taskVersionRepository;

    @Autowired
    public TaskVersionServiceImpl(final TaskVersionRepository taskVersionRepository) {
        this.taskVersionRepository = taskVersionRepository;
    }

    @Override
    public TaskVersion get(final String versionId) {
        final TaskVersion taskVersion = taskVersionRepository.findById(versionId);
        if (taskVersion == null) {
            log.debug("TaskVersion with id=[{}] not found", versionId);
            throw new NotFoundException("TaskVersion with id=["+ versionId +"] not found");
        }
        return taskVersion;
    }

}
