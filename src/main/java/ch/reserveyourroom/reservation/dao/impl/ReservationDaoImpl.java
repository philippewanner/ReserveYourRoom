package ch.reserveyourroom.reservation.dao.impl;

import ch.reserveyourroom.common.dao.impl.GenericDaoImpl;
import ch.reserveyourroom.reservation.dao.ReservationDao;
import ch.reserveyourroom.reservation.model.Reservation;

import javax.ejb.Stateless;

@Stateless
public class ReservationDaoImpl extends GenericDaoImpl<Reservation, Long> implements ReservationDao {
}
