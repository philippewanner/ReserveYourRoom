package ch.reserveyourroom.common.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * Entities in this application has to extend this abstract class, so that they are common methods and
 * fields for all entities.
 */

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    @Id
    private UUID uuid;

    public UUID getUuid() {

        return uuid;
    }

    public abstract int hashCode();

    public abstract boolean equals(Object obj);

    public abstract String toString();
}
