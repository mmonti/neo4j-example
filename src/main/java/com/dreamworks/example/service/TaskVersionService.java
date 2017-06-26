package com.dreamworks.example.service;

import com.dreamworks.example.model.TaskVersion;

/**
 * Created by mmonti on 4/20/17.
 */
public interface TaskVersionService {

    /**
     *
     * @param versionId
     * @return
     */
    TaskVersion get(final String versionId);

}
