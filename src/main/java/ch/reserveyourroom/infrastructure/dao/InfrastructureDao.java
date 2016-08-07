package ch.reserveyourroom.infrastructure.dao;

import ch.reserveyourroom.common.dao.GenericDao;
import ch.reserveyourroom.infrastructure.model.Infrastructure;

import javax.ejb.Stateless;

/**
 * Specific data access object for the Infrastructure entity.
 */
@Stateless
public interface InfrastructureDao extends GenericDao<Infrastructure, Long> {
}
