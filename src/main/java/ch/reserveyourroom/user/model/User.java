package ch.reserveyourroom.user.model;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.persistence.*;
import javax.validation.OverridesAttribute;

/**
 * Entity that represent a user model.
 */

@Entity
@Table(name = "USERS")
@SequenceGenerator(name = AbstractEntity.GENERATOR, sequenceName = "SQ_USERS")
@AttributeOverride(name = "id", column = @Column(name = "USER_ID"))
public class User extends AbstractEntity<Long> {

    @Column(name = "USER_FIRSTNAME", nullable = false)
    private String firstname;

    @Column(name = "USER_LASTNAME", nullable = false)
    private String lastname;

    @Column(name = "USER_EMAIL", nullable = false, unique = true)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {

        return "User [id=" + getId() + "name=" + firstname + " " + lastname + " email=" + email + "]";
    }
}
