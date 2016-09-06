
package ch.reserveyourroom.wish.dao.impl;

import ch.reserveyourroom.address.dao.AddressDao;
import ch.reserveyourroom.address.dao.impl.AddressDaoImpl;
import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.dao.impl.BuildingDaoImpl;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.room.dao.RoomDao;
import ch.reserveyourroom.room.dao.impl.RoomDaoImpl;
import ch.reserveyourroom.room.model.Room;
import ch.reserveyourroom.user.dao.UserDao;
import ch.reserveyourroom.user.dao.impl.UserDaoImpl;
import ch.reserveyourroom.user.model.User;
import ch.reserveyourroom.wish.dao.WishDao;
import ch.reserveyourroom.wish.model.Wish;
import junit.framework.TestCase;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * Unit testing the wish dao implementation.
 */



@RunWith(MockitoJUnitRunner.class)
public class WishDaoImplTest extends TestCase{

    private WishDao wishDao = new WishDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private RoomDao roomDao = new RoomDaoImpl();
    private BuildingDao buildingDao = new BuildingDaoImpl();

    protected static EntityManagerFactory emf;
    protected EntityManager em;

    private User user;
    private Building building;
    private Room room;

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

        wishDao.setEntityManager(em);
        userDao.setEntityManager(em);
        roomDao.setEntityManager(em);
        buildingDao.setEntityManager(em);

        em.getTransaction().begin();

        // Create a user
        user = new User();
        user.setEmail("email");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        userDao.create(user);

        // Create a building's address
        Address a1 = new Address();
        a1.setCity("city");
        a1.setCountry("country");
        a1.setHousenumber("2a");
        a1.setState("state");
        a1.setStreet("street");
        a1.setZipcode("1964");

        // Create a building's room
        room = new Room();
        room.setFloor(2);
        room.setName("r");
        room.setSeatnumber(23);
        room.setSize(67f);

        // Create the building
        building = new Building();
        building.setAddress(a1);
        building.setName("b");
        Set<Room> rooms = new TreeSet<>();
        rooms.add(room);
        building.setRooms(rooms);

        buildingDao.create(building);
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
        createSampleWishInDb(nbObjectToCreate);

        // When
        final long count = wishDao.countAll();

        // Then
        assertTrue("The number of objects has to be two", nbObjectToCreate == count);
    }

    @Test
    public void should_createANewObjectInTheDb() {

        // Given

        // When
        final String objectId = createSampleWishInDb(1).get(0);

        // Then
        Wish objectRead = wishDao.read(objectId).get();
        assertTrue("The Id of the object can not be read", objectId.compareTo(objectRead.getUuid().toString()) == 0);
    }

    @Test
    public void should_loadAllObjectsFromDb() {

        // Given
        int nbObjectToCreate = 5;
        this.createSampleWishInDb(nbObjectToCreate);

        // When
        final List<Wish> objectsFound = wishDao.loadAll();

        // Then
        assertTrue("The objects size created has to match the objects found", nbObjectToCreate == objectsFound.size());
    }

    @Test
    public void should_deleteObjectFromDb() {

        // Given
        String pk = this.createSampleWishInDb(1).get(0);
        Optional<Wish> objectFound = wishDao.read(pk);

        // When
        wishDao.delete(objectFound.get().getUuid());

        // Then
        assertFalse("The object has to be deleted from the DB", wishDao.read(pk).isPresent());
    }

    @Test
    public void should_updateObjectFromDb() {

        // Given
        String pk = this.createSampleWishInDb(1).get(0);
        Optional<Wish> objectFound = wishDao.read(pk);
        LocalDate end = LocalDate.now();

        // When
        try {
            objectFound.get().setEnd(Date.valueOf(end));
            wishDao.update(objectFound.get());
        } catch (EntityOptimisticLockException e) {
            e.printStackTrace();
        }

        // Then
        assertTrue("The update can not occur", wishDao.read(pk).get().getEnd().compareTo(Date.valueOf(end)) == 0);
    }

    @Test
    public void should_readObjectFromDb() {

        // Given
        String pk = this.createSampleWishInDb(1).get(0);

        // When
        Optional<Wish> objectFound = wishDao.read(pk);

        // Then
        assertTrue("The system cannot read the object from DB", objectFound.isPresent());
    }

    private List<String> createSampleWishInDb(int numberOfObject){

        List<String> ids = new ArrayList<>();

        for(int i=0; i<numberOfObject; i++) {

            Wish w1 = new Wish();
            w1.setStart(Date.valueOf(LocalDate.of(2016, Month.JANUARY, 01)));
            w1.setEnd(Date.valueOf(LocalDate.of(2016, Month.APRIL, 20)));
            w1.setRoom(room);
            w1.setUser(user);

            String id = wishDao.create(w1);
            ids.add(id);
        }

        return ids;
    }
}
