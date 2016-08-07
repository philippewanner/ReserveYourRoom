package ch.reserveyourroom.wish.dao;

import ch.reserveyourroom.common.dao.GenericDao;
import ch.reserveyourroom.wish.model.Wish;

import java.util.Date;
import java.util.List;

/**
 * Specific data access object for the Wish entity.
 */

public interface WishDao extends GenericDao<Wish, Long> {

    List<Wish> findByEnd(Date end);

    List<Wish> findByStart(Date start);

    List<Wish> findByRoomId(Long roomId);
}
