package ch.reserveyourroom.reservation.model;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entity that represent a reservation model.
 */

@Entity
@Table(name = "RESERVATION")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_RESERVATION")
@AttributeOverride(name = "id", column = @Column(name = "RESERVATION_ID"))
public class Reservation extends AbstractEntity<Long> {

    @NotNull
    @Column(name = "RESERVATION_START", nullable = false)
    private Date start;

    @NotNull
    @Column(name = "RESERVATION_END", nullable = false)
    private Date end;

    @Override
    public String toString() {

        return "Reservation [id=" + getId() + ", start=" + start + ", end=" + end + "]";
    }
}