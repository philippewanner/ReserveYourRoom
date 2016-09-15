package ch.reserveyourroom.reservation.dao.impl;

import ch.reserveyourroom.address.dao.AddressDao;
import ch.reserveyourroom.address.dao.impl.AddressDaoImpl;
import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.dao.impl.BuildingDaoImpl;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.reservation.dao.ReservationDao;
import ch.reserveyourroom.reservation.model.Reservation;
import ch.reserveyourroom.room.dao.RoomDao;
import ch.reserveyourroom.room.dao.impl.RoomDaoImpl;
import ch.reserveyourroom.room.model.Room;
import ch.reserveyourroom.user.dao.UserDao;
import ch.reserveyourroom.user.dao.impl.UserDaoImpl;
import ch.reserveyourroom.user.model.User;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
 * Unit testing the reservation dao implementation.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReservationDaoImplTest {

    private ReservationDao reservationDao = new ReservationDaoImpl();
    private BuildingDao buildingDao = new BuildingDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    private AddressDao addressDao = new AddressDaoImpl();
    private RoomDao roomDao = new RoomDaoImpl();

    protected static EntityManagerFactory emf;
    protected EntityManager em;

    private UUID userId;
    private UUID buildingId;
    private UUID roomId;

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

        reservationDao.setEntityManager(em);
        buildingDao.setEntityManager(em);
        userDao.setEntityManager(em);
        roomDao.setEntityManager(em);
        addressDao.setEntityManager(em);

        em.getTransaction().begin();

        // Create a user
        User user = new User();
        user.setEmail("email");
        user.setFirstname("firstname");
        user.setLastname("lastname");
        userId = userDao.create(user);

        // Create a building's address
        Address a1 = new Address();
        a1.setCity("city");
        a1.setCountry("country");
        a1.setHousenumber("2a");
        a1.setState("state");
        a1.setStreet("street");
        a1.setZipcode("1964");
        UUID addressId = addressDao.create(a1);

        // Create the building
        Building building = new Building();
        building.setAddressId(addressId);
        building.setName("b");
        buildingId = buildingDao.create(building);

        // Create a building's room
        Room room = new Room();
        room.setFloor(2);
        room.setName("r");
        room.setSeatnumber(23);
        room.setSize(67f);
        room.setBuildingId(buildingId);
        roomId = roomDao.create(room);


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
            createSampleReservationInDb();
        }

        // When
        final long count = reservationDao.countAll();

        // Then
        assertTrue("The number of objects has to be two", nbObjectToCreate == count);
    }

    @Test
    public void should_createANewObjectInTheDb() {

        // Given

        // When
        final UUID objectId = createSampleReservationInDb();

        // Then
        Reservation objectRead = reservationDao.read(objectId).get();
        assertTrue("The Id of the object can not be read", objectId.compareTo(objectRead.getUuid()) == 0);
    }

    @Test
    public void should_loadAllObjectsFromDb() {

        // Given
        int nbObjectToCreate = 5;
        for (int i = 0; i < nbObjectToCreate; i++) {
            this.createSampleReservationInDb();
        }

        // When
        final List<Reservation> objectsFound = reservationDao.loadAll();

        // Then
        assertTrue("The objects size created has to match the objects found", nbObjectToCreate == objectsFound.size());
    }

    @Test
    public void should_deleteObjectFromDb() {

        // Given
        UUID pk = this.createSampleReservationInDb();
        Optional<Reservation> objectFound = reservationDao.read(pk);

        // When
        reservationDao.delete(objectFound.get().getUuid());

        // Then
        assertFalse("The object has to be deleted from the DB", reservationDao.read(pk).isPresent());
    }

    @Test
    public void should_updateObjectFromDb() {

        // Given
        UUID pk = this.createSampleReservationInDb();
        Optional<Reservation> objectFound = reservationDao.read(pk);
        LocalDate end = LocalDate.now();

        // When
        try {
            objectFound.get().setEnd(Date.valueOf(end));
            reservationDao.update(objectFound.get());
        } catch (EntityOptimisticLockException e) {
            e.printStackTrace();
        }

        // Then
        assertTrue("The update can not occur", reservationDao.read(pk).get().getEnd().compareTo(Date.valueOf(end)) == 0);
    }

    @Test
    public void should_readObjectFromDb() {

        // Given
        UUID pk = this.createSampleReservationInDb();

        // When
        Optional<Reservation> objectFound = reservationDao.read(pk);

        // Then
        assertTrue("The system cannot read the object from DB", objectFound.isPresent());
    }

    private UUID createSampleReservationInDb() {

        Reservation reservation = new Reservation();
        LocalDate end = LocalDate.now();
        reservation.setEnd(Date.valueOf(end));
        LocalDate start = LocalDate.of(2016, Month.APRIL, 1);
        reservation.setStart(Date.valueOf(start));
        reservation.setRoomId(roomId);
        reservation.setUserId(userId);

        return reservationDao.create(reservation);
    }
}
