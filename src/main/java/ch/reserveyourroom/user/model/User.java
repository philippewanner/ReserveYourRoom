package ch.reserveyourroom.user.model;

import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.reservation.model.Reservation;
import ch.reserveyourroom.wish.model.Wish;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.OverridesAttribute;
import java.util.List;

/**
 * Entity that represent a user model.
 */

@Entity
@Table(name = "USERS")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_USERS")
@AttributeOverride(name = "id", column = @Column(name = "USER_ID"))
public class User extends AbstractEntity<Long> {

    @Nullable
    @Column(name = "USER_FIRSTNAME", nullable = false)
    private String firstname;

    @Nullable
    @Column(name = "USER_LASTNAME", nullable = false)
    private String lastname;

    @NotEmpty
    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String email;

    @Nullable
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "WHISH_ID")
    private List<Wish> whishes;

    @Nullable
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "RESERVATION_ID")
    private List<Reservation> reservations;

    @Nullable
    public List<Wish> getWhishes() {
        return whishes;
    }

    public void setWhishes(@Nullable List<Wish> whishes) {
        this.whishes = whishes;
    }

    @Override
    public String toString() {

        return "User [id=" + getId() + "name=" + firstname + " " + lastname + " email=" + email + "]";
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

    public void setReservations(@Nullable List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Nullable
    public List<Reservation> getReservations() {
        return reservations;
    }
}
