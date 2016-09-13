package ch.reserveyourroom.infrastructure.endpoint;

import ch.reserveyourroom.common.endpoint.GenericEndpoint;
import ch.reserveyourroom.common.endpoint.ResponseFactory;
import ch.reserveyourroom.common.endpoint.Routes;
import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
import ch.reserveyourroom.infrastructure.service.InfrastructureService;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Infrastructure Endpoint
 */
@Path(Routes.INFRASTRUCTURE)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class InfrastructureEndpoint implements GenericEndpoint<Infrastructure> {

    @Inject
    private InfrastructureService service;

    @Inject
    private Logger logger;

    @GET
    @Path(Routes.PING)
    public Response ping() {

        return ResponseFactory.buildSuccessResponse(BusinessOperation.PING);
    }

    @POST
    public Response save(@NotNull final Infrastructure infrastructure) {

        String savedEntityId = this.service.save(infrastructure);
        return ResponseFactory.buildSuccessResponse(BusinessOperation.SAVE, savedEntityId);
    }

    @GET
    public Response getAll() {

        List<Infrastructure> entities = this.service.loadAll();
        return ResponseFactory.buildSuccessResponse(BusinessOperation.LOAD_ALL, entities);
    }

    @GET
    @Path("/{id}")
    public Response getById(@NotNull @PathParam("id") final String id) {

        try {
            final Infrastructure infrastructure = this.service.find(id);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.FIND, infrastructure);

        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);

        }
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@NotNull @PathParam("id") final String id, @NotNull final Infrastructure entity) {

        try {
            Infrastructure entityUpdated = this.service.update(entity);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.UPDATE, entityUpdated);
        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);
        }
    }
}