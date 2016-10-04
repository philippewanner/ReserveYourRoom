package ch.reserveyourroom.reservation.endpoint;

import ch.reserveyourroom.common.endpoint.GenericEndpoint;
import ch.reserveyourroom.common.endpoint.ResponseFactory;
import ch.reserveyourroom.common.endpoint.Routes;
import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.common.mail.Mailservice;
import ch.reserveyourroom.reservation.model.Reservation;
import ch.reserveyourroom.reservation.service.ReservationService;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

/**
 * Reservation Endpoint
 */
@Path(Routes.RESERVATION)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Stateless
public class ReservationEndpoint implements GenericEndpoint<Reservation> {

    @Inject
    private ReservationService service;

    private Logger logger = LoggerFactory.getLogger(ReservationEndpoint.class);

    @GET
    @Path(Routes.PING)
    public Response ping() {

        return ResponseFactory.buildSuccessResponse(BusinessOperation.PING);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@NotNull final Reservation reservation) {

        logger.info("Room id: "+ reservation.getUuid() + " saved");
        UUID savedEntityId = this.service.save(reservation);

        //send a mail to the user
        try {
            Mailservice.sendMail("danijel.brei@students.bfh.ch", "Reservation confirmation", "reservation for room "+ reservation.getRoomId() +" has been created.");
        } catch(EmailException emx)
        {
            System.out.printf("Exception during save-prozess. Exception-msg: %s", emx.getMessage());
        }

        return ResponseFactory.buildSuccessResponse(BusinessOperation.SAVE, savedEntityId);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/remove/{id}")
    public Response delete(@PathParam("id") final UUID id) {
        try {
            this.service.delete(id);
            logger.info("Room id: "+ id + " deleted");

            //send a mail to the user
            try {
                Mailservice.sendMail("danijel.brei@students.bfh.ch", "Reservation canceled", "reservation has been canceled.");
            } catch(EmailException emx)
            {
                System.out.printf("Exception during delete-process. Exception-msg: %s", emx.getMessage());
            }

            return ResponseFactory.buildSuccessResponse(BusinessOperation.DELETE);
        } catch (BusinessUnprocessableOperationException e) {
            logger.error("Error when deleting room id: "+ id);
            return ResponseFactory.buildClientErrorResponse(e);
        } finally {
            logger.info("Operation delete end");
        }
    }

    @GET
    public Response getAll() {

        List<Reservation> entities = this.service.loadAll();
        return ResponseFactory.buildSuccessResponse(BusinessOperation.LOAD_ALL, entities);
    }

    @GET
    @Path("/{id}")
    public Response getById(@NotNull @PathParam("id") final UUID id) {

        try {
            final Reservation reservation = this.service.find(id);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.FIND, reservation);

        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);

        }
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@NotNull @PathParam("id") final UUID id, @NotNull final Reservation entity) {

        try {
            Reservation entityUpdated = this.service.update(entity);
            return ResponseFactory.buildSuccessResponse(BusinessOperation.UPDATE, entityUpdated);
        } catch (BusinessUnprocessableOperationException e) {
            return ResponseFactory.buildClientErrorResponse(e);
        }
    }
}