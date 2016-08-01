package ch.reserveyourroom.user.dao;

import ch.reserveyourroom.common.dao.GenericDao;
import ch.reserveyourroom.user.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Specific data access object for the User entity.
 */
public interface UserDao {

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String firstname);

    List<User> findByEmail(String firstname);
}
