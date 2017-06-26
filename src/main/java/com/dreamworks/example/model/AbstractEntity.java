package com.dreamworks.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Property;

import java.util.Date;

/**
 * Created by mmonti on 8/25/16.
 */
@ToString(of = "id")
@EqualsAndHashCode(of = {"id"})
public abstract class AbstractEntity implements Identifiable {

    /**
     * This represents the id neo4j use to store a node. We should not rely in this attribute.
     */
    @GraphId
    @Getter
    @JsonIgnore
    private Long nodeId;

    /**
     * This represents the ID of the entity.
     */
    @Getter
    @Setter
    @Property
    private String id;

    @Getter
    @Setter
    @Property
    private Date createdOn;

}
