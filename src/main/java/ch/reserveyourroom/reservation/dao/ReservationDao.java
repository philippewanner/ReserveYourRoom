package ch.reserveyourroom.reservation.dao;

import ch.reserveyourroom.common.dao.GenericDao;
import ch.reserveyourroom.reservation.model.Reservation;

import javax.ejb.Stateless;

/**
 * Specific data access object for the Reservation entity.
 */
@Stateless
public interface ReservationDao extends GenericDao<Reservation> {
}
