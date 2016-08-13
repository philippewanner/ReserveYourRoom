package ch.reserveyourroom.infrastructure.dao.impl;

import ch.reserveyourroom.common.dao.impl.GenericDaoImpl;
import ch.reserveyourroom.infrastructure.dao.InfrastructureDao;
import ch.reserveyourroom.infrastructure.model.Infrastructure;

import javax.ejb.Stateless;

@Stateless
public class InfrastructureDaoImpl extends GenericDaoImpl<Infrastructure> implements InfrastructureDao {
}
