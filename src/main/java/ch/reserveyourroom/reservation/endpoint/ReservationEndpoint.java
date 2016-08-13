package ch.reserveyourroom.reservation.endpoint;

import ch.reserveyourroom.common.endpoint.GenericEndpoint;
import ch.reserveyourroom.common.endpoint.ResponseFactory;
import ch.reserveyourroom.common.endpoint.Routes;
import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.reservation.model.Reservation;
import ch.reserveyourroom.reservation.service.ReservationService;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Reservation Endpoint
 */
@Path(Routes.WISH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class ReservationEndpoint implements GenericEndpoint<Reservation> {

    @Inject
    private ReservationService service;

    @Inject
    private Logger log;

    @GET
    @Path(Routes.PING)
    public Response ping() {

        return ResponseFactory.buildSuccessResponse(BusinessOperation.PING);
    }

    @POST
    public Response save(@NotNull final Reservation reservation) {

        String savedEntityId = this.service.save(reservation);
        return ResponseFactory.buildSuccessResponse(BusinessOperation.SAVE, savedEntityId);
    }

    @GET
    public Response getAll() {

        List<Reservation> entities = this.service.loadAll();
        return ResponseFactory.buildSuccessResponse(BusinessOperation.LOAD_ALL, entities);
    }

    @GET
    @Path("/{id}")
    public Response getById(@NotNull @PathParam("id") final String id) {

        try {
            final Reservation reservation = this.service.find(id);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.FIND, reservation);

        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);

        }
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@NotNull @PathParam("id") final String id, @NotNull final Reservation entity) {

        try {
            Reservation entityUpdated = this.service.update(entity);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.UPDATE, entityUpdated);
        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);
        }
    }
}