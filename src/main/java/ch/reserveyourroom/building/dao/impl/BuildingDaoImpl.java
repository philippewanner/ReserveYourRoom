package ch.reserveyourroom.building.dao.impl;

import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.dao.impl.GenericDaoImpl;

import javax.ejb.Stateless;

@Stateless
public class BuildingDaoImpl extends GenericDaoImpl<Building, Long> implements BuildingDao {
}
