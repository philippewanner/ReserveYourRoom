package ch.reserveyourroom.wish.model;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.model.AbstractEntity;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
import ch.reserveyourroom.room.model.Room;
import ch.reserveyourroom.user.model.User;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity that represent a whish model.
 */

@Entity
@Table(name = "WHISHES")
@AttributeOverride(name = "uuid", column = @Column(name = "WHISH_ID"))
public class Wish extends AbstractEntity implements Comparable<Wish> {

    @NotNull
    @Column(name = "WHISH_START", nullable = false)
    private Date start;

    @NotNull
    @Column(name = "WHISH_END", nullable = false)
    private Date end;

    @Nullable
    @Column(name = "ROOM_ID", nullable = true)
    private UUID roomId;

    @Nullable
    @Column(name = "USER_ID", nullable = true)
    private UUID userId;

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 13 + (start != null ? start.hashCode() : 0);
        hash = hash * 7 + (end != null ? end.hashCode() : 0);
        hash = hash * 13 + (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Wish)) return false;

        Wish other = (Wish) o;
        return  (this.start != null && this.start.equals(other.start)) &&
                (this.end != null && this.end.equals(other.end)) &&
                (this.roomId != null && this.roomId.equals(other.roomId));

    }

    @Override
    public String toString() {

        return "Whish [id=" + getUuid() + ", start=" + start + ", end=" + end + "]";
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStart(){
        return this.start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Date getEnd(){
        return this.end;
    }

    @Override
    public int compareTo(Wish o) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if ( this == o ) return EQUAL;

        if (this.start.before(o.start)) return BEFORE;
        if (this.start.after(o.start)) return AFTER;

        if (this.end.before(o.end)) return BEFORE;
        if (this.end.after(o.end)) return AFTER;

        return EQUAL;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}