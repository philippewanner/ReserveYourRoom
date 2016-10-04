package ch.reserveyourroom.address.service.impl;

import ch.reserveyourroom.address.dao.AddressDao;
import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.address.service.AddressService;
import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
public class AddressServiceImpl implements AddressService {


    private Logger LOGGER = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Inject
    private AddressDao addressDao;

    public Address find(@NotNull UUID key) throws BusinessUnprocessableOperationException {

        Optional<Address> entity = this.addressDao.read(key);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.FIND);
        }
    }

    public Optional<Address> search(@NotNull UUID key) {

        return this.addressDao.read(key);
    }

    public List<Address> loadAll() {

        return this.addressDao.loadAll();
    }

    public UUID save(@NotNull @Valid Address address) {

        return this.addressDao.create(address);
    }

    public Address update(@NotNull @Valid Address address) throws BusinessUnprocessableOperationException {

        try {
            return this.addressDao.update(address);
        } catch (EntityOptimisticLockException e) {
            throw new BusinessUnprocessableOperationException(BusinessOperation.UPDATE, e);
        }

    }

    public void delete(@NotNull UUID id) throws BusinessUnprocessableOperationException {

        Optional<Address> t = this.addressDao.read(id);
        if(t.isPresent()){
            this.addressDao.delete(t.get().getUuid());
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.DELETE);
        }
    }


}
