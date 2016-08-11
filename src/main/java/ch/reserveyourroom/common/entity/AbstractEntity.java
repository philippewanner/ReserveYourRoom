package ch.reserveyourroom.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Entities in this application has to extend this abstract class, so that they are common methods and
 * fields for all entities.
 */

@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> {

    public static final String GENERATOR = "ASSIGNED_SEQUENCE";

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR)
    private PK id;

    public PK getId() {

        return id;
    }

    protected void setId(PK id){

        this.id = id;
    }

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    public abstract String toString();
}
