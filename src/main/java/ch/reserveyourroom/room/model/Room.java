package ch.reserveyourroom.room.model;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.common.entity.AbstractEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity that represent a room model.
 */

@Entity
@Table(name = "ROOMS")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_ROOM")
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

    @Override
    public String toString() {

        return "Room [id=" + getId() + ", name=" + name + "]";
    }
}