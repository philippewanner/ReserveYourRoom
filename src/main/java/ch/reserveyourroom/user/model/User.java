package ch.reserveyourroom.user.model;

import ch.reserveyourroom.common.model.AbstractEntity;
import ch.reserveyourroom.reservation.model.Reservation;
import ch.reserveyourroom.wish.model.Wish;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Entity that represent a user model.
 */

@Entity
@Table(name = "USERS")
@AttributeOverride(name = "uuid", column = @Column(name = "USER_ID"))
public class User extends AbstractEntity {

    @Nullable
    @Column(name = "USER_FIRSTNAME", nullable = false)
    private String firstname;

    @Nullable
    @Column(name = "USER_LASTNAME", nullable = false)
    private String lastname;

    @NotEmpty
    @Column(name = "USER_EMAIL", nullable = false)
    private String email;

    @Nullable
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Wish> whishes;

    @Nullable
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Reservation> reservations;


    public Set<Wish> getWhishes() {
        return whishes;
    }

    public void setWhishes(@Nullable Set<Wish> whishes) {
        this.whishes = whishes;
    }

    @Override
    public String toString() {

        return "User [id=" + getUuid() + "name=" + firstname + " " + lastname + " email=" + email + "]";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public void setLastname(@Nullable String lastname) {
        this.lastname = lastname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public void setFirstname(@Nullable String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname(){
        return this.firstname;
    }

    public void setReservations(@Nullable Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Nullable
    public Set<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public int hashCode() {

        int hash = 3;
        hash = hash * 17 + (getUuid() != null ? getUuid().hashCode() : 0);
        hash = hash * 3 + (firstname != null ? firstname.hashCode() : 0);
        hash = hash * 13 + (lastname != null ? lastname.hashCode() : 0);
        hash = hash * 5 + (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof User)) return false;

        User other = (User) o;
        return Objects.equals(this.getUuid(), other.getUuid()) && (this.firstname != null && this.firstname.equals(other.firstname)) && (this.lastname != null && this.lastname.equals(other.lastname)) && (this.email != null && this.email.equals(other.email)) && (this.whishes != null && this.whishes.equals(other.whishes)) && this.reservations != null && this.reservations.equals(other.reservations);

    }
}
