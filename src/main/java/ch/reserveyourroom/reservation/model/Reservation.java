package ch.reserveyourroom.reservation.model;

import ch.reserveyourroom.common.model.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

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
    @Column(name = "ROOM_ID", nullable = false)
    private UUID roomId;

    @NotNull
    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Override
    public String toString() {

        return "Reservation [id=" + getUuid() + ", start=" + start + ", end=" + end + "]";
    }

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 13 + (start != null ? start.hashCode() : 0);
        hash = hash * 7 + (end != null ? end.hashCode() : 0);
        hash = hash * 5 + (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Reservation)) return false;

        Reservation other = (Reservation) o;
        return  (this.start != null && this.start.equals(other.start)) &&
                (this.end != null && this.end.equals(other.end)) &&
                (this.roomId != null && this.roomId.equals(other.roomId));

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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID user) {
        this.userId = user;
    }

    public UUID getRoomId(){
        return this.roomId;
    }

    public void setRoomId(UUID room) {
        this.roomId = room;
    }
}