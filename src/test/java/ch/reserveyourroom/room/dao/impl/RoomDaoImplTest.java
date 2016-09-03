package ch.reserveyourroom.room.dao.impl;

import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.room.dao.RoomDao;
import ch.reserveyourroom.room.model.Room;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
 * Unit testing the room dao implementation.
 */
@RunWith(MockitoJUnitRunner.class)
public class RoomDaoImplTest {

    private RoomDao roomDao = new RoomDaoImpl();

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
        for (int i = 0; i < nbObjectToCreate; i++) {
            createSampleRoomInDb();
        }

        // When
        final long count = roomDao.countAll();

        // Then
        assertTrue("The number of objects has to be two", nbObjectToCreate == count);
    }

    @Test
    public void should_createANewObjectInTheDb() {

        // Given

        // When
        final String objectId = createSampleRoomInDb();

        // Then
        Room objectRead = roomDao.read(objectId).get();
        assertTrue("The Id of the object can not be read", objectId.compareTo(objectRead.getUuid().toString()) == 0);
    }

    @Test
    public void should_loadAllObjectsFromDb() {

        // Given
        int nbObjectToCreate = 5;
        for (int i = 0; i < nbObjectToCreate; i++) {
            this.createSampleRoomInDb();
        }

        // When
        final List<Room> objectsFound = roomDao.loadAll();

        // Then
        assertTrue("The objects size created has to match the objects found", nbObjectToCreate == objectsFound.size());
    }

    @Test
    public void should_deleteObjectFromDb() {

        // Given
        String pk = this.createSampleRoomInDb();
        Optional<Room> objectFound = roomDao.read(pk);

        // When
        roomDao.delete(objectFound.get().getUuid());

        // Then
        assertFalse("The object has to be deleted from the DB", roomDao.read(pk).isPresent());
    }

    @Test
    public void should_updateObjectFromDb() {

        // Given
        String pk = this.createSampleRoomInDb();
        Optional<Room> objectFound = roomDao.read(pk);
        String newRoomName = "newRoomName";

        // When
        try {
            objectFound.get().setName(newRoomName);
            roomDao.update(objectFound.get());
        } catch (EntityOptimisticLockException e) {
            e.printStackTrace();
        }

        // Then
        assertTrue("The update can not occur", roomDao.read(pk).get().getName().compareTo(newRoomName) == 0);
    }

    @Test
    public void should_readObjectFromDb() {

        // Given
        String pk = this.createSampleRoomInDb();

        // When
        Optional<Room> objectFound = roomDao.read(pk);

        // Then
        assertTrue("The system cannot read the object from DB", objectFound.isPresent());
        assertFalse("The system cannot read the object from DB", objectFound.get().getName().isEmpty());
    }

    private String createSampleRoomInDb() {

        Room r = new Room();
        r.setFloor(2);
        r.setName("roomName");
        r.setSeatnumber(198);
        r.setSize((float) (34));

        return roomDao.create(r);
    }
}
