package ch.reserveyourroom.building.dao.impl;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
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
            createSampleBuildingInDb();
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
        final String objectId = createSampleBuildingInDb();

        // Then
        Building objectRead = buildingDao.read(objectId).get();
        assertTrue("The Id of the object can not be read", objectId.compareTo(objectRead.getUuid().toString()) == 0);
    }

    @Test
    public void should_loadAllObjectsFromDb() {

        // Given
        int nbObjectToCreate = 5;
        for(int i=0; i<nbObjectToCreate; i++){
            this.createSampleBuildingInDb();
        }

        // When
        final List<Building> objectsFound = buildingDao.loadAll();

        // Then
        assertTrue("The objects size created has to match the objects found", nbObjectToCreate == objectsFound.size());
    }

    @Test
    public void should_deleteObjectFromDb() {

        // Given
        String pk = this.createSampleBuildingInDb();
        Optional<Building> objectFound = buildingDao.read(pk);

        // When
        buildingDao.delete(objectFound.get().getUuid());

        // Then
        assertFalse("The object has to be deleted from the DB", buildingDao.read(pk).isPresent());
    }

    @Test
    public void should_updateObjectFromDb() {

        // Given
        String pk = this.createSampleBuildingInDb();
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
        String pk = this.createSampleBuildingInDb();

        // When
        Optional<Building> objectFound = buildingDao.read(pk);

        // Then
        assertTrue("The system cannot read the object from DB", objectFound.isPresent());
    }

    private String createSampleBuildingInDb(){

        Address a1 = new Address();
        a1.setCity("city");
        a1.setCountry("country");
        a1.setHousenumber("2a");
        a1.setState("state");
        a1.setStreet("street");
        a1.setZipcode("1964");

        final Set<Room> rooms = new TreeSet<>();
        final int nbRoomToCreate = 0;
        for(int i=0; i<nbRoomToCreate; i++){
            Room r = new Room();
            r.setFloor(2);
            r.setName("room"+i);
            r.setSeatnumber(i*10);
            r.setSize((float)(i*i));
            rooms.add(r);
        }

        Building b = new Building();
        b.setAddress(a1);
        b.setName("name");
        b.setRooms(rooms);

        return buildingDao.create(b);
    }
}
