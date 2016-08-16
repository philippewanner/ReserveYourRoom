package ch.reserveyourroom.util;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class PersistenceHelper {

    private static final EntityManager entityManager;

    static {
        entityManager = Persistence.createEntityManagerFactory("myPersistenceUnitTest").createEntityManager();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }
}