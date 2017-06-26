package com.dreamworks.example.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mmonti on 6/23/17.
 */
@Data
@NoArgsConstructor
@ToString(of = {"id"})
@NodeEntity(label = "VERSION")
public class TaskVersion extends AbstractEntity implements UTI {

    @JsonManagedReference
    @Relationship(type = "IN_TASK", direction = Relationship.OUTGOING)
    private Task task;

    @Relationship(type = "PARENT", direction = Relationship.OUTGOING)
    private TaskVersion parent;

    @Relationship(type = "CHILD", direction = Relationship.OUTGOING)
    private List<TaskVersion> childs = new ArrayList<>();

    /**
     *
     * @param id
     * @param task
     */
    public TaskVersion(final String id, final Task task) {
        setId(id);
        setTask(task);
        setCreatedOn(new Date());

        setParent(null);
        setChilds(new ArrayList<>());

        task.addTaskVersion(this);
    }

    /**
     *
     * @param id
     * @param previousVersion
     */
    public TaskVersion(final String id, final Task task, final TaskVersion previousVersion) {
        this(id, task);

        setParent(previousVersion);
        previousVersion.add(this);
    }

    /**
     *
     * @param childVersion
     * @return
     */
    public TaskVersion add(final TaskVersion childVersion) {
        if (!childs.contains(childVersion)) {
            childs.add(childVersion);
        }
        return this;
    }

    /**
     *
     * @return
     */
    @Override
    public String getUti() {
        if (task != null) {
            return task.getUti();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
