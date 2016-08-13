package ch.reserveyourroom.room.service.impl;

import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.room.dao.RoomDao;
import ch.reserveyourroom.room.model.Room;
import ch.reserveyourroom.room.service.RoomService;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Stateless
public class RoomServiceImpl implements RoomService {


    private Logger LOGGER = LoggerFactory.getLogger(RoomServiceImpl.class);

    @Inject
    private RoomDao roomDao;

    public Room find(@NotNull String key) throws BusinessUnprocessableOperationException {

        Optional<Room> entity = this.roomDao.read(key);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.FIND);
        }
    }

    public Optional<Room> search(@NotNull String key) {

        return this.roomDao.read(key);
    }

    public List<Room> loadAll() {

        return this.roomDao.loadAll();
    }

    public String save(@NotNull @Valid Room room) {

        return this.roomDao.create(room);
    }

    public Room update(@NotNull @Valid Room room) throws BusinessUnprocessableOperationException {

        try {
            return this.roomDao.update(room);
        } catch (EntityOptimisticLockException e) {
            throw new BusinessUnprocessableOperationException(BusinessOperation.UPDATE, e);
        }

    }

    public void delete(@NotEmpty String id) throws BusinessUnprocessableOperationException {

        Optional<Room> t = this.roomDao.read(id);
        if(t.isPresent()){
            this.roomDao.delete(t.get());
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.DELETE);
        }
    }

}
