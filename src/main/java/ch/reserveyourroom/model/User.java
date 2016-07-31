package ch.reserveyourroom.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by philippe.wanner on 19/07/16.
 */

@Entity
@Table(name = "users")
public class User {

    private String email;

    @Id
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
