package ch.reserveyourroom.room.model;

import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
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
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_ROOMS")
@AttributeOverride(name = "id", column = @Column(name = "ROOM_ID"))
public class Room extends AbstractEntity<Long> {

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
    private List<Infrastructure> infrastructures;

    @Override
    public String toString() {

        return "Room [id=" + getId() + ", name=" + name + "]";
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
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Room)) return false;

        Room other = (Room) o;
        return Objects.equals(this.getId(), other.getId()) && (this.name != null && this.name.equals(other.name)) && (this.floor != null && this.floor.equals(other.floor)) && (this.size != null && this.size.equals(other.size)) && (this.seatnumber != null && this.seatnumber.equals(other.seatnumber)) && (this.floor != null && this.floor.equals(other.floor)) && this.infrastructures != null && this.infrastructures.equals(other.infrastructures);

    }
}