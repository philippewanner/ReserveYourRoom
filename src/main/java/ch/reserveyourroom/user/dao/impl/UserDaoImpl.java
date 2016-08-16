package ch.reserveyourroom.user.dao.impl;

import ch.reserveyourroom.common.dao.impl.GenericDaoImpl;
import ch.reserveyourroom.user.dao.UserDao;
import ch.reserveyourroom.user.model.User;

import javax.ejb.Stateless;

@Stateless
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    /*
    public List<User> findByFirstname(String firstname) {
        throw new NotImplementedException(); //@TODO: to implement
    }

    public List<User> findByLastname(String firstname) {
        throw new NotImplementedException(); //@TODO: to implement
    }

    public List<User> findByEmail(String firstname) {
        throw new NotImplementedException(); //@TODO: to implement
    }
    */
}
