package ch.reserveyourroom.common.exception.persistence;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.ejb.ApplicationException;

/**
 * Exception that expresses that an entity could has been already locked in the database by another process and
 * the transaction could not been proceed as requested.
 */
@ApplicationException(rollback = true)
public class EntityOptimisticLockException extends PersistenceException {

    public EntityOptimisticLockException(AbstractEntity entity, Exception e) {

        super("The " + entity.toString() + " could not have been persisted", e);
    }
}
