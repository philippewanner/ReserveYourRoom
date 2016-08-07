package ch.reserveyourroom.common.exception.persistence;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.ejb.ApplicationException;

/**
 * Class EntityNotFoundException that expresses an exception when the application could not find the desired entity.
 */
@ApplicationException(rollback = true)
public class EntityNotFoundException extends PersistenceException {

    public EntityNotFoundException(AbstractEntity entity, Exception e){

        super("The " + entity.toString() + " could not be found", e);
    }
}
