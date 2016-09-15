package ch.reserveyourroom.building.service.impl;

import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.building.service.BuildingService;
import ch.reserveyourroom.common.exception.business.BusinessOperation;
import ch.reserveyourroom.common.exception.business.BusinessUnprocessableOperationException;
import ch.reserveyourroom.common.exception.persistence.EntityNotFoundException;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.common.exception.persistence.PersistenceException;
import ch.reserveyourroom.common.exception.persistence.EntityUnprocessableException;
import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.building.service.BuildingService;
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
public class BuildingServiceImpl implements BuildingService {


    private Logger LOGGER = LoggerFactory.getLogger(BuildingServiceImpl.class);

    @Inject
    private BuildingDao buildingDao;

    public Building find(@NotNull UUID key) throws BusinessUnprocessableOperationException {

        Optional<Building> entity = this.buildingDao.read(key);
        if (entity.isPresent()) {
            return entity.get();
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.FIND);
        }
    }

    public Optional<Building> search(@NotNull UUID key) {

        return this.buildingDao.read(key);
    }

    public List<Building> loadAll() {

        return this.buildingDao.loadAll();
    }

    public UUID save(@NotNull @Valid Building building) {

        return this.buildingDao.create(building);
    }

    public Building update(@NotNull @Valid Building building) throws BusinessUnprocessableOperationException {

        try {
            return this.buildingDao.update(building);
        } catch (EntityOptimisticLockException e) {
            throw new BusinessUnprocessableOperationException(BusinessOperation.UPDATE, e);
        }

    }

    public void delete(@NotEmpty UUID id) throws BusinessUnprocessableOperationException {

        Optional<Building> t = this.buildingDao.read(id);
        if(t.isPresent()){
            this.buildingDao.delete(t.get().getUuid());
        } else {
            throw new BusinessUnprocessableOperationException(BusinessOperation.DELETE);
        }
    }


}
