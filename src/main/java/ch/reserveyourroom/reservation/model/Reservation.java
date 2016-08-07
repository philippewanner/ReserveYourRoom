package ch.reserveyourroom.reservation.model;

import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.room.model.Room;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

/**
 * Entity that represent a reservation model.
 */

@Entity
@Table(name = "RESERVATIONS")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_RESERVATIONS")
@AttributeOverride(name = "id", column = @Column(name = "RESERVATION_ID"))
public class Reservation extends AbstractEntity<Long> {

    @NotNull
    @Column(name = "RESERVATION_START", nullable = false)
    private Date start;

    @NotNull
    @Column(name = "RESERVATION_END", nullable = false)
    private Date end;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Override
    public String toString() {

        return "Reservation [id=" + getId() + ", start=" + start + ", end=" + end + "]";
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Reservation)) return false;

        Reservation other = (Reservation) o;
        return Objects.equals(this.getId(), other.getId()) && (this.start != null && this.start.equals(other.start)) && (this.end != null && this.end.equals(other.end)) && this.room != null && this.room.equals(other.room);

    }
}