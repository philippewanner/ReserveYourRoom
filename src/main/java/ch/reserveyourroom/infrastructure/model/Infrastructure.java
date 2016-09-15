package ch.reserveyourroom.infrastructure.model;

import ch.reserveyourroom.common.model.AbstractEntity;
import ch.reserveyourroom.room.model.Room;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity that represent a reservation model.
 */

@Entity
@Table(name = "INFRASTRUCTURES")
@AttributeOverride(name = "uuid", column = @Column(name = "INFRASTRUCTURE_ID"))
public class Infrastructure extends AbstractEntity implements Comparable<Infrastructure>{

    @NotEmpty
    @Column(name = "INFRASTRUCTURE_NAME", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "ROOM_ID", nullable = false)
    private UUID roomId;

    @Override
    public String toString() {

        return "Infrastructure [id=" + getUuid() + ", " + name + "]";
    }

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 3 + (name != null ? name.hashCode() : 0);
        hash = hash * 5 + (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Infrastructure)) return false;

        Infrastructure other = (Infrastructure) o;
        return Objects.equals(this.getName(), other.getName());

    }

    @Override
    public int compareTo(@NotNull Infrastructure o) {
        return this.name.compareTo(o.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }
}