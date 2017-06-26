package com.dreamworks.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
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
@NodeEntity(label = "TASK")
public class Task extends AbstractEntity implements UTI {

    @Property
    private String uti;

    @Relationship(type = "HAS_VERSIONS", direction = Relationship.OUTGOING)
    private List<TaskVersion> versions = new ArrayList<>();

    @Relationship(type = "CURRENT", direction = Relationship.OUTGOING)
    private TaskVersion current;

    /**
     *
     * @param id
     * @param uti
     */
    public Task(final String id, final String uti) {
        setId(id);
        setUti(uti);
        setCreatedOn(new Date());
    }

    /**
     *
     * @return
     */
    public TaskVersion current() {
        return current;
    }

    /**
     *
     * @return
     */
    public boolean hasVersions() {
        return !versions.isEmpty();
    }

    /**
     *
     * @param taskVersion
     */
    public void addTaskVersion(final TaskVersion taskVersion) {
        if (!versions.contains(taskVersion)) {
            versions.add(taskVersion);

            setCurrent(taskVersion);
        }
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
