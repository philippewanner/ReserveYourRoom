package ch.reserveyourroom.room.model;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

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

    @Nullable
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "INFRASTRUCTURE_ID")
    private List<Infrastructure> infrastructures;

    @Override
    public String toString() {

        return "Room [id=" + getId() + ", name=" + name + "]";
    }
}