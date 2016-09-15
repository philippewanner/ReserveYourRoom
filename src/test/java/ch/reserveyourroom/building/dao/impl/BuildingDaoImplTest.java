package ch.reserveyourroom.building.dao.impl;

import ch.reserveyourroom.address.dao.AddressDao;
import ch.reserveyourroom.address.dao.impl.AddressDaoImpl;
import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.room.dao.RoomDao;
import ch.reserveyourroom.room.dao.impl.RoomDaoImpl;
import ch.reserveyourroom.room.model.Room;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
 * Unit testing the building dao implementation.
 */
@RunWith(MockitoJUnitRunner.class)
public class BuildingDaoImplTest {


    private BuildingDao buildingDao = new BuildingDaoImpl();
    private RoomDao roomDao = new RoomDaoImpl();
    private AddressDao addressDao = new AddressDaoImpl();

    protected static EntityManagerFactory emf;
    protected EntityManager em;

    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("myPersistenceUnitTest");
    }

    @AfterClass
    public static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    @Before
    public void beginTransaction() {

        em = emf.createEntityManager();

        buildingDao.setEntityManager(em);
        addressDao.setEntityManager(em);
        roomDao.setEntityManager(em);

        em.getTransaction().begin();
    }

    @After
    public void rollbackTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }

        if (em.isOpen()) {
            em.close();
        }
    }

    @Test
    public void should_countAllObjects() {
        // Given
        int nbObjectToCreate = 2;
        for(int i=0; i<nbObjectToCreate; i++) {
            createSampleBuildingInDb(""+i);
        }

        // When
        final long count = buildingDao.countAll();

        // Then
        assertTrue("The number of objects has to be two", nbObjectToCreate == count);
    }

    @Test
    public void should_createANewObjectInTheDb() {

        // Given

        // When
        final UUID objectId = createSampleBuildingInDb("");

        // Then
        Building objectRead = buildingDao.read(objectId).get();
        assertTrue("The Id of the object can not be read", objectId.compareTo(objectRead.getUuid()) == 0);
    }

    @Test
    public void should_loadAllObjectsFromDb() {

        // Given
        int nbObjectToCreate = 5;
        for(int i=0; i<nbObjectToCreate; i++){
            this.createSampleBuildingInDb("");
        }

        // When
        final List<Building> objectsFound = buildingDao.loadAll();

        // Then
        assertTrue("The objects size created has to match the objects found", nbObjectToCreate == objectsFound.size());
    }

    @Test
    public void should_deleteObjectFromDb() {

        // Given
        UUID pk = this.createSampleBuildingInDb("");
        Optional<Building> objectFound = buildingDao.read(pk);

        // When
        buildingDao.delete(objectFound.get().getUuid());

        // Then
        assertFalse("The object has to be deleted from the DB", buildingDao.read(pk).isPresent());
    }

    @Test
    public void should_updateObjectFromDb() {

        // Given
        UUID pk = this.createSampleBuildingInDb("");
        Optional<Building> objectFound = buildingDao.read(pk);
        String newBuildingName = "newBuildingName";

        // When
        try {
            objectFound.get().setName(newBuildingName);
            buildingDao.update(objectFound.get());
        } catch (EntityOptimisticLockException e) {
            e.printStackTrace();
        }

        // Then
        assertTrue("The update can not occur", buildingDao.read(pk).get().getName().compareTo(newBuildingName) == 0);
    }

    @Test
    public void should_readObjectFromDb() {

        // Given
        UUID pk = this.createSampleBuildingInDb("");

        // When
        Optional<Building> objectFound = buildingDao.read(pk);

        // Then
        assertTrue("The system cannot read the object from DB", objectFound.isPresent());
    }

    private UUID createSampleBuildingInDb(String buildingName){

        Address a1 = new Address();
        a1.setCity("city");
        a1.setCountry("country");
        a1.setHousenumber("2a");
        a1.setState("state");
        a1.setStreet("street");
        a1.setZipcode("1964");
        UUID addressId = addressDao.create(a1);

        Building b = new Building();
        b.setAddressId(addressId);
        b.setName("name"+buildingName);
        UUID buildingId = buildingDao.create(b);

        final int nbRoomToCreate = 4;
        for(int i=0; i<nbRoomToCreate; i++){
            Room r = new Room();
            r.setFloor(2);
            r.setName("room"+i+buildingName);
            r.setSeatnumber(i*10);
            r.setSize((float)(i*i));
            r.setBuildingId(buildingId);
            roomDao.create(r);
        }

        return buildingId;
    }
}
