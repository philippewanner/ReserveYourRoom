package ch.reserveyourroom.common.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Entities in this application has to extend this abstract class, so that they are common methods and
 * fields for all entities.
 */

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    public String getId() {

        return id;
    }

    protected void setId(String id){

        this.id = id;
    }

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    public abstract String toString();
}
