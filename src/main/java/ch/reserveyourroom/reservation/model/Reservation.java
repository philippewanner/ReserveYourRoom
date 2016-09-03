package ch.reserveyourroom.reservation.model;

import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.room.model.Room;

import javax.annotation.Nullable;
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

        int hash = 1;
        hash = hash * 3 + (getUuid() != null ? getUuid().hashCode() : 0);
        hash = hash * 13 + (start != null ? start.hashCode() : 0);
        hash = hash * 7 + (end != null ? end.hashCode() : 0);
        hash = hash * 5 + (room != null ? room.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Reservation)) return false;

        Reservation other = (Reservation) o;
        return Objects.equals(this.getUuid(), other.getUuid()) && (this.start != null && this.start.equals(other.start)) && (this.end != null && this.end.equals(other.end)) && this.room != null && this.room.equals(other.room);

    }

    public Date getStart(){
        return this.start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd(){
        return this.end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Room getRoom(){
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}