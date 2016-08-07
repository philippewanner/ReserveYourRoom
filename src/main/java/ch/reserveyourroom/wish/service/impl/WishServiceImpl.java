package ch.reserveyourroom.wish.service.impl;

import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.wish.dao.WishDao;
import ch.reserveyourroom.wish.model.Wish;
import ch.reserveyourroom.wish.service.WishService;
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
public class WishServiceImpl implements WishService {


    private Logger LOGGER = LoggerFactory.getLogger(WishServiceImpl.class);

    @Inject
    private WishDao wishDao;

    public Wish find(@NotNull Long key) throws BusinessUnprocessableOperationException {

        Optional<Wish> entity = this.wishDao.read(key);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.FIND);
        }
    }

    public Optional<Wish> search(@NotNull Long key) {

        return this.wishDao.read(key);
    }

    public List<Wish> loadAll() {

        return this.wishDao.loadAll();
    }

    public Long save(@NotNull @Valid Wish wish) {

        return this.wishDao.create(wish);
    }

    public Wish update(@NotNull @Valid Wish wish) throws BusinessUnprocessableOperationException {

        try {
            return this.wishDao.update(wish);
        } catch (EntityOptimisticLockException e) {
            throw new BusinessUnprocessableOperationException(BusinessOperation.UPDATE, e);
        }

    }

    public void delete(@NotEmpty Long id) throws BusinessUnprocessableOperationException {

        Optional<Wish> t = this.wishDao.read(id);
        if(t.isPresent()){
            this.wishDao.delete(t.get());
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.DELETE);
        }
    }


}
