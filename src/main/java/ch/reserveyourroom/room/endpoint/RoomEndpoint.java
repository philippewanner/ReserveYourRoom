package ch.reserveyourroom.room.endpoint;

import ch.reserveyourroom.common.endpoint.GenericEndpoint;
import ch.reserveyourroom.common.endpoint.ResponseFactory;
import ch.reserveyourroom.common.endpoint.Routes;
import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.room.model.Room;
import ch.reserveyourroom.room.service.RoomService;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Room Endpoint
 */
@Path(Routes.WISH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class RoomEndpoint implements GenericEndpoint<Room> {

    @Inject
    private RoomService service;

    @Inject
    private Logger logger;

    @GET
    @Path(Routes.PING)
    public Response ping() {

        return ResponseFactory.buildSuccessResponse(BusinessOperation.PING);
    }

    @POST
    public Response save(@NotNull final Room room) {

        String savedEntityId = this.service.save(room);
        return ResponseFactory.buildSuccessResponse(BusinessOperation.SAVE, savedEntityId);
    }

    @GET
    public Response getAll() {

        List<Room> entities = this.service.loadAll();
        return ResponseFactory.buildSuccessResponse(BusinessOperation.LOAD_ALL, entities);
    }

    @GET
    @Path("/{id}")
    public Response getById(@NotNull @PathParam("id") final String id) {

        try {
            final Room room = this.service.find(id);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.FIND, room);

        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);

        }
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@NotNull @PathParam("id") final String id, @NotNull final Room entity) {

        try {
            Room entityUpdated = this.service.update(entity);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.UPDATE, entityUpdated);
        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);
        }
    }
}