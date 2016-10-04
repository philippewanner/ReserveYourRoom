package ch.reserveyourroom.user.endpoint;

import ch.reserveyourroom.common.endpoint.GenericEndpoint;
import ch.reserveyourroom.common.endpoint.ResponseFactory;
import ch.reserveyourroom.common.endpoint.Routes;
import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.user.model.User;
import ch.reserveyourroom.user.service.UserService;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * User Endpoint
 */
@Path(Routes.USER)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class UserEndpoint implements GenericEndpoint<User> {

    @Inject
    private UserService service;

    @Inject
    private Logger log;

    @GET
    @Path(Routes.PING)
    public Response ping() {

        return Response.status(Response.Status.OK).build();
    }

    @POST
    public Response save(@NotNull final User user) {

        UUID savedEntityId = this.service.save(user);
        return ResponseFactory.buildSuccessResponse(BusinessOperation.SAVE, savedEntityId);
    }

    @GET
    public Response getAll() {

        List<User> users = this.service.loadAll();
        return ResponseFactory.buildSuccessResponse(BusinessOperation.LOAD_ALL, users);
    }

    @GET
    @Path("/{id}")
    public Response getById(@NotNull @PathParam("id") final UUID id) {

        try {
            final User user = this.service.find(id);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.FIND, user);

        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);

        }
    }

    @Override
    public Response delete(@NotNull UUID id) {
        return null;
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@NotNull @PathParam("id") final UUID id, @NotNull final User entity) {

        try {
            User entityUpdated = this.service.update(entity);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.UPDATE, entityUpdated);
        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);
        }
    }
}