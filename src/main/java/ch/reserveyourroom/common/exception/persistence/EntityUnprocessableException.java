package ch.reserveyourroom.common.exception.persistence;

import ch.reserveyourroom.common.model.AbstractEntity;

import javax.ejb.ApplicationException;

/**
 * Entity not valid exception.
 */
@ApplicationException(rollback = true)
public class EntityUnprocessableException extends PersistenceException {

    public EntityUnprocessableException(AbstractEntity entity, Exception e) {

        super("The " + entity.toString() + " could not have been processed, probably entity not valid.", e);
    }
}
