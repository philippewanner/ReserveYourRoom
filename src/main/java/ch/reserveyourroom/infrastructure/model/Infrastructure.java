package ch.reserveyourroom.infrastructure.model;

import ch.reserveyourroom.common.model.AbstractEntity;
import ch.reserveyourroom.room.model.Room;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID", nullable = true)
    private Room room;

    @Override
    public String toString() {

        return "Infrastructure [id=" + getUuid() + "]";
    }

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 3 + (getUuid() != null ? getUuid().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(!(o instanceof Infrastructure)) return false;

        Infrastructure other = (Infrastructure) o;
        return Objects.equals(this.getUuid(), other.getUuid());

    }

    @Override
    public int compareTo(Infrastructure o) {
        return this.name.compareTo(o.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}