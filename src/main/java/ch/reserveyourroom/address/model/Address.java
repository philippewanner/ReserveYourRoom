package ch.reserveyourroom.address.model;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Entity that represent an address model.
 */

@Entity
@Table(name = "ADDRESSES")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_ADDRESSES")
@AttributeOverride(name = "id", column = @Column(name = "ADDRESS_ID"))
public class Address extends AbstractEntity<Long> {

    @NotNull
    @Column(name = "ADDRESS_STREET", nullable = false)
    private String street;

    @NotNull
    @Column(name = "ADDRESS_CITY", nullable = false)
    private String city;

    @Nullable
    @Column(name = "ADDRESS_STATE", nullable = true)
    private String state;

    @NotNull
    @Column(name = "ADDRESS_ZIPCODE", nullable = false)
    private String zipcode;

    @NotNull
    @Column(name = "ADDRESS_COUNTRY", nullable = false)
    private String country;

    @Nullable
    @Column(name = "ADDRESS_HOUSENUM", nullable = true)
    private String housenumber;


    @Override
    public String toString() {

        return "Address [id=" + getId() + ", street=" + street + ", housenumber=" + housenumber + ", zip=" + zipcode + ", city=" + city + ", state=" + state + ", country=" + country + "]";
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity(){
        return this.city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet(){
        return this.street;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry(){
        return this.country;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    public String getHousenumber(){
        return this.housenumber;
    }
}
