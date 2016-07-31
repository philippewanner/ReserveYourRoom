package ch.reserveyourroom.service;

import ch.reserveyourroom.model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by philippe.wanner on 19/07/16.
 */
@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        return query.getResultList();
    }
}
