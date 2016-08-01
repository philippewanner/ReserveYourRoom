package ch.reserveyourroom.common.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Entities in this application has to extend this abstract class, so that they are common methods and
 * fields for all entities.
 */

@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> {

    public static final String GENERATOR = "ASSIGNED_SEQUENCE";


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR)
    private PK id;

    public PK getId() {

        return id;
    }

    protected void setId(PK id){

        this.id = id;
    }

    @Override
    public int hashCode() {

        if (getId() != null) {

            return getId().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {

            return true;
        }
        if (obj == null) {

            return false;
        }
        if (getClass() != obj.getClass()) {

            return false;
        }
        AbstractEntity<?> other = (AbstractEntity<?>) obj;
        if (getId() == null || other.getId() == null) {

            return false;
        }
        if (!getId().equals(other.getId())) {

            return false;
        }
        return true;
    }
}
