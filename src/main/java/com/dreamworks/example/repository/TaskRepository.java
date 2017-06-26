package com.dreamworks.example.repository;

import com.dreamworks.example.model.Task;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by mmonti on 4/20/17.
 */
@Repository
public interface TaskRepository extends GraphRepository<Task> {

    /**
     *
     * @param taskId
     * @return
     */
    @Depth(value = 2)
    Task findById(final String taskId);

}
