package ch.reserveyourroom.user.service.impl;

import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.user.dao.UserDao;
import ch.reserveyourroom.user.model.User;
import ch.reserveyourroom.user.service.UserService;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
public class UserServiceImpl implements UserService {

    @Inject
    private Logger logger;

    @Inject
    private UserDao userDao;

    public User find(@NotNull UUID key) throws BusinessUnprocessableOperationException {

        Optional<User> entity = this.userDao.read(key);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.FIND);
        }
    }

    public Optional<User> search(@NotNull UUID key) {

        return this.userDao.read(key);
    }

    public List<User> loadAll() {

        return this.userDao.loadAll();
    }

    public UUID save(@NotNull @Valid User user) {

        return this.userDao.create(user);
    }

    public User update(@NotNull @Valid User user) throws BusinessUnprocessableOperationException {

        try {
            return this.userDao.update(user);
        } catch (EntityOptimisticLockException e) {
            throw new BusinessUnprocessableOperationException(BusinessOperation.UPDATE, e);
        }
    }

    public void delete(@NotNull UUID id) throws BusinessUnprocessableOperationException {

        Optional<User> t = this.userDao.read(id);
        if(t.isPresent()){
            this.userDao.delete(t.get().getUuid());
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.DELETE);
        }
    }
}
