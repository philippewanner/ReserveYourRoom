package ch.reserveyourroom.common.endpoint;

import ch.reserveyourroom.common.entity.AbstractEntity;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;

/**
 * All REST endpoints have to implements these methods.
 */
public interface GenericEndpoint<T extends AbstractEntity> {

    Response ping();

    Response save(@NotNull final T entity);

    Response getAll();

    Response getById(@NotNull final String id);

    Response updateById(@NotNull final String id, @NotNull final T entity);
}
