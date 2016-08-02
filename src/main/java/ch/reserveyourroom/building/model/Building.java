package ch.reserveyourroom.building.model;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.room.model.Room;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Entity that represent a building model.
 */

@Entity
@Table(name = "BUILDINGS")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_BUILDING")
@AttributeOverride(name = "id", column = @Column(name = "BUILDING_ID"))
public class Building extends AbstractEntity<Long> {

    @NotNull
    @Column(name = "BUILDING_NAME", nullable = false)
    private String name;

    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="ROOM_ID")
    private List<Room> rooms;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="ADDRESS_ID")
    private Address address;


    @Override
    public String toString() {

        return "Building [id=" + getId() + ", name=" + name + "]";
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}