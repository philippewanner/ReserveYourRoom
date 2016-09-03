package ch.reserveyourroom.infrastructure.service.impl;

import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.infrastructure.dao.InfrastructureDao;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
import ch.reserveyourroom.infrastructure.service.InfrastructureService;
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
public class InfrastructureServiceImpl implements InfrastructureService {


    private Logger LOGGER = LoggerFactory.getLogger(InfrastructureServiceImpl.class);

    @Inject
    private InfrastructureDao infrastructureDao;

    public Infrastructure find(@NotNull String key) throws BusinessUnprocessableOperationException {

        Optional<Infrastructure> entity = this.infrastructureDao.read(key);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.FIND);
        }
    }

    public Optional<Infrastructure> search(@NotNull String key) {

        return this.infrastructureDao.read(key);
    }

    public List<Infrastructure> loadAll() {

        return this.infrastructureDao.loadAll();
    }

    public String save(@NotNull @Valid Infrastructure infrastructure) {

        return this.infrastructureDao.create(infrastructure);
    }

    public Infrastructure update(@NotNull @Valid Infrastructure infrastructure) throws BusinessUnprocessableOperationException {

        try {
            return this.infrastructureDao.update(infrastructure);
        } catch (EntityOptimisticLockException e) {
            throw new BusinessUnprocessableOperationException(BusinessOperation.UPDATE, e);
        }

    }

    public void delete(@NotEmpty String id) throws BusinessUnprocessableOperationException {

        Optional<Infrastructure> t = this.infrastructureDao.read(id);
        if(t.isPresent()){
            this.infrastructureDao.delete(t.get().getUuid());
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.DELETE);
        }
    }


}
