package ch.reserveyourroom.infrastructure.model;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Entity that represent a reservation model.
 */

@Entity
@Table(name = "INFRASTRUCTURES")
@AttributeOverride(name = "uuid", column = @Column(name = "INFRASTRUCTURE_ID"))
public class Infrastructure extends AbstractEntity {

    @Override
    public String toString() {

        return "Infrastructure [id=" + getUuid() + "]";
    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Infrastructure)) return false;

        Infrastructure other = (Infrastructure) o;
        return Objects.equals(this.getUuid(), other.getUuid());

    }
}