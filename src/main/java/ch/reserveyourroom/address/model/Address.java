package ch.reserveyourroom.address.model;

import ch.reserveyourroom.common.model.AbstractEntity;

import javax.annotation.Nullable;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity that represent an address model.
 */

@Entity
@Table(name = "ADDRESSES")
@AttributeOverride(name = "uuid", column = @Column(name = "ADDRESS_ID"))
public class Address extends AbstractEntity {

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

        return "Address [id=" + getUuid() + ", street=" + street + ", housenumber=" + housenumber + ", zip=" + zipcode + ", city=" + city + ", state=" + state + ", country=" + country + "]";
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

    @Override
    public int hashCode() {

        int hash = 1;
        hash = hash * 3 + (getUuid() != null ? getUuid().hashCode() : 0);
        hash = hash * 29 + (street != null ? street.hashCode() : 0);
        hash = hash * 7 + (city != null ? city.hashCode() : 0);
        hash = hash * 5 + (state != null ? state.hashCode() : 0);
        hash = hash * 17 + (zipcode != null ? zipcode.hashCode() : 0);
        hash = hash * 13 + (country != null ? country.hashCode() : 0);
        hash = hash * 23 + (housenumber != null ? housenumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Address)) return false;

        Address other = (Address) o;
        return Objects.equals(this.getUuid(), other.getUuid()) && (this.street != null && this.street.equals(other.street)) && (this.city != null && this.city.equals(other.city)) && (this.state != null && this.state.equals(other.state)) && (this.zipcode != null && this.zipcode.equals(other.zipcode)) && (this.country != null && this.country.equals(other.country)) && this.housenumber != null && this.housenumber.equals(other.housenumber);

    }
}
