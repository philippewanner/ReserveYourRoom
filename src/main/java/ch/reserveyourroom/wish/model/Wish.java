package ch.reserveyourroom.wish.model;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
import ch.reserveyourroom.room.model.Room;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Entity that represent a whish model.
 */

@Entity
@Table(name = "WHISHES")
@AttributeOverride(name = "uuid", column = @Column(name = "WHISH_ID"))
public class Wish extends AbstractEntity {

    @NotNull
    @Column(name = "WHISH_START", nullable = false)
    private Date start;

    @NotNull
    @Column(name = "WHISH_END", nullable = false)
    private Date end;

    @Nullable
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "INFRASTRUCTURE_ID")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Infrastructure> infrastructures;

    @Nullable
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ADDRESS_ID")
    private Address address;

    @Nullable
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BUILDING_ID")
    private Building building;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 3 + (getUuid() != null ? getUuid().hashCode() : 0);
        hash = hash * 13 + (start != null ? start.hashCode() : 0);
        hash = hash * 7 + (end != null ? end.hashCode() : 0);
        hash = hash * 5 + (building != null ? building.hashCode() : 0);
        hash = hash * 13 + (room != null ? room.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Wish)) return false;

        Wish other = (Wish) o;
        return Objects.equals(this.getUuid(), other.getUuid()) && (this.start != null && this.start.equals(other.start)) && (this.end != null && this.end.equals(other.end)) && (this.infrastructures != null && this.infrastructures.equals(other.infrastructures)) && (this.address != null && this.address.equals(other.address)) && (this.building != null && this.building.equals(other.building)) && this.room != null && this.room.equals(other.room);

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

    @Nullable
    public List<Infrastructure> getInfrastructures() {
        return infrastructures;
    }

    public void setInfrastructures(@Nullable List<Infrastructure> infrastructures) {
        this.infrastructures = infrastructures;
    }

    @Nullable
    public Address getAddress() {
        return address;
    }

    public void setAddress(@Nullable Address address) {
        this.address = address;
    }

    @Nullable
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(@Nullable Building building) {
        this.building = building;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}