package ch.reserveyourroom.common.endpoint;

import ch.reserveyourroom.common.model.AbstractEntity;
import ch.reserveyourroom.common.exception.business.BusinessException;
import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.common.exception.persistence.EntityNotFoundException;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.common.exception.persistence.EntityUnprocessableException;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

import static ch.reserveyourroom.common.exception.business.BusinessOperation.*;

/**
 * Response factory for the endpoint in this application.
 */
public final class ResponseFactory {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFactory.class);

    private ResponseFactory() {

    }

    public static Response buildSuccessResponse(BusinessOperation op) {

        HttpStatusCode status = getSuccessResponse(op);

        return Response.status(status.getStatusCode()).build();
    }

    public static Response buildSuccessResponse(BusinessOperation op, AbstractEntity entity) {

        HttpStatusCode status = getSuccessResponse(op);

        return Response.status(status.getStatusCode()).entity(entity).build();
    }

    public static Response buildSuccessResponse(BusinessOperation op, UUID id) {

        HttpStatusCode status = getSuccessResponse(op);

        return Response.status(status.getStatusCode()).entity(id).build();
    }

    public static Response buildSuccessResponse(BusinessOperation op, List<? extends AbstractEntity> entities) {

        HttpStatusCode status = getSuccessResponse(op);

        return Response.status(status.getStatusCode()).entity(entities).build();
    }

    private static HttpStatusCode getSuccessResponse(BusinessOperation op){

        HttpStatusCode status;

        if(DELETE == op) { status = HttpStatusCode.OK; }
        else if(FIND == op) { status = HttpStatusCode.OK; }
        else if(LOAD_ALL == op) { status = HttpStatusCode.OK; }
        else if(SAVE == op) { status = HttpStatusCode.CREATED; }
        else if(SEARCH == op) { status = HttpStatusCode.OK; }
        else if(UPDATE == op) { status = HttpStatusCode.OK; }
        // Other
        else { status = HttpStatusCode.OK; }

        return status;
    }

    public static Response buildClientErrorResponse(Exception e) {

        HttpStatusCode status;

        // BusinessException
        if(e instanceof BusinessUnprocessableOperationException) { status = HttpStatusCode.BAD_REQUEST; }
        else if(e instanceof BusinessException) { status = HttpStatusCode.BAD_REQUEST; }
        // Persistence Exception
        else if(e instanceof EntityNotFoundException) { status = HttpStatusCode.BAD_REQUEST; }
        else if(e instanceof EntityOptimisticLockException) { status = HttpStatusCode.BAD_REQUEST; }
        else if(e instanceof EntityUnprocessableException) { status = HttpStatusCode.BAD_REQUEST; }
        // Other
        else { status = HttpStatusCode.BAD_REQUEST; }

        return Response.status(status.getStatusCode()).entity(e.getMessage()).build();
    }

    public static Response buildServerErrorResponse(Exception e) {

        HttpStatusCode status;

        if(e instanceof NotImplementedException) { status = HttpStatusCode.NOT_IMPLEMENTED; }
        else if(e instanceof NotAllowedException) { status = HttpStatusCode.NOT_ACCEPTABLE; }
        // Other
        else { status = HttpStatusCode.INTERNAL_SERVER_ERROR; }


        return Response.status(status.getStatusCode()).entity(e.getMessage()).build();
    }
}