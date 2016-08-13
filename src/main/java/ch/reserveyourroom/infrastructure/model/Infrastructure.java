package ch.reserveyourroom.infrastructure.model;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * Entity that represent a reservation model.
 */

@Entity
@Table(name = "INFRASTRUCTURES")
@AttributeOverride(name = "id", column = @Column(name = "INFRASTRUCTURE_ID"))
public class Infrastructure extends AbstractEntity {

    @Override
    public String toString() {

        return "Infrastructure [id=" + getId() + "]";
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Infrastructure)) return false;

        Infrastructure other = (Infrastructure) o;
        return Objects.equals(this.getId(), other.getId());

    }
}