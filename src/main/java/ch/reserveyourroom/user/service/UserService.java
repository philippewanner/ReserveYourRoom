package ch.reserveyourroom.user.service;

import ch.reserveyourroom.user.dao.UserDao;
import ch.reserveyourroom.user.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class UserService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

    public List<User> getAll() {
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    public void save(User user) {
        em.persist(user);
    }
}
