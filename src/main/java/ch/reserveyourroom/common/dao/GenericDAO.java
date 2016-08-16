package ch.reserveyourroom.common.dao;

import ch.reserveyourroom.common.entity.AbstractEntity;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface that defines the CRUD operations that can perform each entity.
 * @param <T> The given entity to perform the operations on.
 */
public interface GenericDao<T extends AbstractEntity> {

    /**
     * Count the number of entries from a table that meet some criteria (predicate)
     * @param predicate a predicate to meet
     * @return the number of records meeting the criteria.
     */
    long countAll(final Predicate predicate);

    /**
     * Load and return all entities from the database corresponding to this entity.
     * @return list of all entities found
     */
    List<T> loadAll();

    /**
     * Persist the entity instance object into the database.
     * @param t the entity instance to persist.
     * @return the primary key of the created entity instance.
     */
    String create(T t);

    /**
     * Remove an object from persistent storage in the database.
     * @param t the persistent object to remove.
     */
    void delete(T t);

    /**
     * Retrieve an entity instance object that was previously persisted to the database using the indicated id as primary key.
     * @param id the primary key to retrieve the entity instance.
     * @return an Optional which contains the entity object found, empty otherwise.
     */

    Optional<T> read(String id);

    /**
     * Save changes made to a persistent object.
     * @param t the transient object to update.
     * @return the old state of the object before the update.
     */
    T update(T t) throws EntityOptimisticLockException;

    /**
     * This method is used only for unit tests purpose, to
     * inject entity manager into EJB3 stateless bean.
     * @param em the entity manager to set
     */
    void setEntityManager(EntityManager em);

}
