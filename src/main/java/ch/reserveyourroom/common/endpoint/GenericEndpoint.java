package ch.reserveyourroom.common.endpoint;

import ch.reserveyourroom.common.model.AbstractEntity;

import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * All REST endpoints have to implements these methods.
 */
public interface GenericEndpoint<T extends AbstractEntity> {

    Response ping();

    Response save(@NotNull final T entity);

    Response getAll();

    Response getById(@NotNull final UUID id);

    Response delete(@PathParam("id") @NotNull final UUID id);

    Response updateById(@NotNull final UUID id, @NotNull final T entity);
}
