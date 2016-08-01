package ch.reserveyourroom.user.dao.impl;

import ch.reserveyourroom.common.dao.impl.GenericDaoImpl;
import ch.reserveyourroom.common.exception.ReserveYourRoomOptimisticLockException;
import ch.reserveyourroom.user.dao.UserDao;
import ch.reserveyourroom.user.model.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    public List<User> findByFirstname(String firstname) {
        throw new NotImplementedException(); //@TODO: to implement
    }

    public List<User> findByLastname(String firstname) {
        throw new NotImplementedException(); //@TODO: to implement
    }

    public List<User> findByEmail(String firstname) {
        throw new NotImplementedException(); //@TODO: to implement
    }
}