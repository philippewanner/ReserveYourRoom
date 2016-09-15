package ch.reserveyourroom.building.model;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.common.model.AbstractEntity;
import ch.reserveyourroom.room.model.Room;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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

    @NotNull
    @Column(name = "ADDRESS_ID", nullable = false)
    private UUID addressId;


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

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 13 + (name != null ? name.hashCode() : 0);
        hash = hash * 5 + (addressId != null ? addressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Building)) return false;

        Building other = (Building) o;
        return  (this.name != null && this.name.equals(other.name)) &&
                (this.addressId != null && this.addressId.equals(other.addressId));

    }

    public UUID getAddressId(){
        return this.addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }
}