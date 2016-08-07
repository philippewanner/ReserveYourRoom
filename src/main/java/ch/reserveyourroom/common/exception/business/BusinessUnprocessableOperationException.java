package ch.reserveyourroom.common.exception.business;

/**
 * Class EntityNotFoundException that expresses an exception when the application could not find the desired entity.
 */
public class BusinessUnprocessableOperationException extends BusinessException {

    public BusinessUnprocessableOperationException(BusinessOperation operation, Exception e){

        super("The operation " + operation.name() + " could not been processed", e);
    }

    public BusinessUnprocessableOperationException(BusinessOperation operation){

        super("The operation " + operation.name() + " could not been processed");
    }
}
