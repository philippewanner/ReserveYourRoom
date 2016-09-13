package ch.reserveyourroom.building.model;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.common.model.AbstractEntity;
import ch.reserveyourroom.room.model.Room;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

/**
 * Entity that represent a building model.
 */

@Entity
@Table(name = "BUILDINGS")
@AttributeOverride(name = "uuid", column = @Column(name = "BUILDING_ID"))
public class Building extends AbstractEntity {

    @NotNull
    @Column(name = "BUILDING_NAME", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "building")
    private Set<Room> rooms;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="ADDRESS_FOR_BUILDING", nullable = false)
    private Address address;


    @Override
    public String toString() {

        return "Building [id=" + getUuid() + ", name=" + name + "]";
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 3 + (getUuid() != null ? getUuid().hashCode() : 0);
        hash = hash * 13 + (name != null ? name.hashCode() : 0);
        hash = hash * 7 + (rooms != null ? rooms.hashCode() : 0);
        hash = hash * 5 + (address != null ? address.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Building)) return false;

        Building other = (Building) o;
        return Objects.equals(this.getUuid(), other.getUuid()) && (this.name != null && this.name.equals(other.name)) && (this.rooms != null && this.rooms.equals(other.rooms)) && this.address != null && this.address.equals(other.address);

    }
}