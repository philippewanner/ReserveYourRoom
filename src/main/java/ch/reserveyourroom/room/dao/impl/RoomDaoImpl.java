package ch.reserveyourroom.room.dao.impl;

import ch.reserveyourroom.common.dao.impl.GenericDaoImpl;
import ch.reserveyourroom.room.dao.RoomDao;
import ch.reserveyourroom.room.model.Room;

import javax.ejb.Stateless;

@Stateless
public class RoomDaoImpl extends GenericDaoImpl<Room> implements RoomDao {
}
