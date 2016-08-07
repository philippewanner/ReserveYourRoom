package ch.reserveyourroom.common.exception.persistence;

import javax.ejb.ApplicationException;

/**
 * Technical persistence exception.
 */
@ApplicationException(rollback = true)
public class PersistenceException extends Exception {

    public PersistenceException(String msg, Exception e) {

        super(msg, e);
    }

}