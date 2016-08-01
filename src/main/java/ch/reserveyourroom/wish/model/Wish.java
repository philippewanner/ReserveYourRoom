package ch.reserveyourroom.wish.model;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entity that represent a whish model.
 */

@Entity
@Table(name = "WHISH")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_WHISH")
@AttributeOverride(name = "id", column = @Column(name = "WHISH_ID"))
public class Wish extends AbstractEntity<Long> {

    @NotNull
    @Column(name = "WHISH_START", nullable = false)
    private Date start;

    @NotNull
    @Column(name = "WHISH_END", nullable = false)
    private Date end;

    @Override
    public String toString() {

        return "Whish [id=" + getId() + ", start=" + start + ", end=" + end + "]";
    }
}