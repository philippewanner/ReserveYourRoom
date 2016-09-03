package ch.reserveyourroom.reservation.dao.impl;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.reservation.dao.ReservationDao;
import ch.reserveyourroom.reservation.model.Reservation;
import ch.reserveyourroom.room.model.Room;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.company.Company;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
 * Unit testing the reservation dao implementation.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReservationDaoImplTest {

    private ReservationDao reservationDao = new ReservationDaoImpl();

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

        reservationDao.setEntityManager(em);

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
        final String objectId = createSampleReservationInDb();

        // Then
        Reservation objectRead = reservationDao.read(objectId).get();
        assertTrue("The Id of the object can not be read", objectId.compareTo(objectRead.getUuid().toString()) == 0);
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
        String pk = this.createSampleReservationInDb();
        Optional<Reservation> objectFound = reservationDao.read(pk);

        // When
        reservationDao.delete(objectFound.get().getUuid());

        // Then
        assertFalse("The object has to be deleted from the DB", reservationDao.read(pk).isPresent());
    }

    @Test
    public void should_updateObjectFromDb() {

        // Given
        String pk = this.createSampleReservationInDb();
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
        String pk = this.createSampleReservationInDb();

        // When
        Optional<Reservation> objectFound = reservationDao.read(pk);

        // Then
        assertTrue("The system cannot read the object from DB", objectFound.isPresent());
    }

    private String createSampleReservationInDb() {

        Room r = new Room();
        r.setFloor(2);
        r.setName("roomName");
        r.setSeatnumber(198);
        r.setSize((float) (34));

        Reservation reservation = new Reservation();
        LocalDate end = LocalDate.now();
        reservation.setEnd(Date.valueOf(end));
        LocalDate start = LocalDate.of(2016, 01, 01);
        reservation.setStart(Date.valueOf(start));
        reservation.setRoom(r);

        return reservationDao.create(reservation);
    }
}
