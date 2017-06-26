package com.dreamworks.example.service;

import com.dreamworks.example.model.Task;
import com.dreamworks.example.model.TaskVersion;
import com.dreamworks.example.model.request.TaskPayload;
import com.dreamworks.example.model.request.TaskVersionPayload;

/**
 * Created by mmonti on 4/20/17.
 */
public interface TaskService {

    /**
     *
     * @param payload
     * @return
     */
    Task create(final TaskPayload payload);

    /**
     *
     * @param taskId
     * @param versionId
     * @param payload
     * @return
     */
    TaskVersion create(final String taskId, final String versionId, final TaskVersionPayload payload);

    /**
     *
     * @param taskId
     * @return
     */
    Task get(final String taskId);

}