package ch.reserveyourroom.room.model;

import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Entity that represent a room model.
 */

@Entity
@Table(name = "ROOMS")
@AttributeOverride(name = "uuid", column = @Column(name = "ROOM_ID"))
public class Room extends AbstractEntity {

    @NotEmpty
    @Column(name = "ROOM_NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "ROOM_SIZE", nullable = false)
    private Float size;

    @Nullable
    @Column(name = "ROOM_SEATNUMBER", nullable = true)
    private Integer seatnumber;

    @NotNull
    @Column(name = "ROOM_FLOOR", nullable = false)
    private Integer floor;

    @Nullable
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "INFRASTRUCTURE_ID")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Infrastructure> infrastructures;

    @Override
    public String toString() {

        return "Room [id=" + getUuid() + ", name=" + name + "]";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    @Nullable
    public Integer getSeatnumber() {
        return seatnumber;
    }

    public void setSeatnumber(@Nullable Integer seatnumber) {
        this.seatnumber = seatnumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Nullable
    public List<Infrastructure> getInfrastructures() {
        return infrastructures;
    }

    public void setInfrastructures(@Nullable List<Infrastructure> infrastructures) {
        this.infrastructures = infrastructures;
    }

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 3 + (getUuid() != null ? getUuid().hashCode() : 0);
        hash = hash * 13 + (name != null ? name.hashCode() : 0);
        hash = hash * 7 + (size != null ? size.hashCode() : 0);
        hash = hash * 5 + (seatnumber != null ? seatnumber.hashCode() : 0);
        hash = hash * 13 + (floor != null ? floor.hashCode() : 0);
        hash = hash * 3 + (infrastructures != null ? infrastructures.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Room)) return false;

        Room other = (Room) o;
        return Objects.equals(this.getUuid(), other.getUuid()) && (this.name != null && this.name.equals(other.name)) && (this.floor != null && this.floor.equals(other.floor)) && (this.size != null && this.size.equals(other.size)) && (this.seatnumber != null && this.seatnumber.equals(other.seatnumber)) && (this.floor != null && this.floor.equals(other.floor)) && this.infrastructures != null && this.infrastructures.equals(other.infrastructures);

    }
}