package ch.reserveyourroom.user.dao;

import ch.reserveyourroom.common.dao.GenericDao;
import ch.reserveyourroom.room.model.Room;
import ch.reserveyourroom.user.model.User;

import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * Specific data access object for the User entity.
 */

public interface UserDao extends GenericDao<User> {
/*
    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String firstname);

    List<User> findByEmail(String firstname);
*/
}
