package ch.reserveyourroom.infrastructure.model;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entity that represent a reservation model.
 */

@Entity
@Table(name = "INFRASTRUCTURE")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_INFRASTRUCTURE")
@AttributeOverride(name = "id", column = @Column(name = "INFRASTRUCTURE_ID"))
public class Infrastructure extends AbstractEntity<Long> {

    @Override
    public String toString() {

        return "Infrastructure [id=" + getId() + "]";
    }
}