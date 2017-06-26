package com.dreamworks.example.repository;

import com.dreamworks.example.model.TaskVersion;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mmonti on 4/20/17.
 */
@Repository
public interface TaskVersionRepository extends GraphRepository<TaskVersion> {

    /**
     *
     * @param taskVersionId
     * @return
     */
    @Depth(value = 2)
    TaskVersion findById(final String taskVersionId);
}
