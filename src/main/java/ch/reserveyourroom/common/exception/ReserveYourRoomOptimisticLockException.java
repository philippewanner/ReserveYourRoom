package ch.reserveyourroom.common.exception;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.ejb.ApplicationException;

/**
 * An entity could not have been persisted into the database and the cause it that there is an optimistic lock exception.
 */
@ApplicationException(rollback = true)
public class ReserveYourRoomOptimisticLockException extends Exception {

    public ReserveYourRoomOptimisticLockException(AbstractEntity entity) {

        super("The following entity could not been persisted: " + entity.toString());
    }
}
