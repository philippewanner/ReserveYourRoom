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
@AttributeOverride(name = "uuid", column = @Column(name = "RESERVATION_ID"))
public class Reservation extends AbstractEntity {

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

        return "Reservation [id=" + getUuid() + ", start=" + start + ", end=" + end + "]";
    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Reservation)) return false;

        Reservation other = (Reservation) o;
        return Objects.equals(this.getUuid(), other.getUuid()) && (this.start != null && this.start.equals(other.start)) && (this.end != null && this.end.equals(other.end)) && this.room != null && this.room.equals(other.room);

    }
}