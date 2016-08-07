package ch.reserveyourroom.common.exception.business;

/**
 * Business exception.
 */
public class BusinessException extends Exception {

    public BusinessException(String msg, Exception e) {

        super(msg, e);
    }

    public BusinessException(String msg) {

        super(msg);
    }

}