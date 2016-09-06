package ch.reserveyourroom.address.dao.impl;

import ch.reserveyourroom.address.dao.AddressDao;
import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.dao.impl.BuildingDaoImpl;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.room.model.Room;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/*
 * Unit testing the address dao implementation.
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressDaoImplTest {

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

        addressDao.setEntityManager(em);

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
            createSampleAddressInDb();
        }

        // When
        final long count = addressDao.countAll();

        // Then
        assertTrue("The number of objects has to be two", nbObjectToCreate == count);
    }

    @Test
    public void should_createANewObjectInTheDb() {

        // Given

        // When
        final String objectId = createSampleAddressInDb();

        // Then
        Address objectRead = addressDao.read(objectId).get();
        assertTrue("The Id of the object can not be read", objectId.compareTo(objectRead.getUuid().toString()) == 0);
    }

    @Test
    public void should_loadAllObjectsFromDb() {

        // Given
        int nbObjectToCreate = 5;
        for(int i=0; i<nbObjectToCreate; i++){
            this.createSampleAddressInDb();
        }

        // When
        final List<Address> objectsFound = addressDao.loadAll();

        // Then
        assertTrue("The objects size created has to match the objects found", nbObjectToCreate == objectsFound.size());
    }

    @Test
    public void should_deleteObjectFromDb() {

        // Given
        String pk = this.createSampleAddressInDb();
        Optional<Address> objectFound = addressDao.read(pk);

        // When
        addressDao.delete(objectFound.get().getUuid());

        // Then
        assertFalse("The object has to be deleted from the DB", addressDao.read(pk).isPresent());
    }

    @Test
    public void should_updateObjectFromDb() {

        // Given
        String pk = this.createSampleAddressInDb();
        Optional<Address> objectFound = addressDao.read(pk);
        String newCity = "newCity";

        // When
        try {
            objectFound.get().setCity(newCity);
            addressDao.update(objectFound.get());
        } catch (EntityOptimisticLockException e) {
            e.printStackTrace();
        }

        // Then
        assertTrue("The update can not occur", addressDao.read(pk).get().getCity().compareTo(newCity) == 0);
    }

    @Test
    public void should_readObjectFromDb() {

        // Given
        String pk = this.createSampleAddressInDb();

        // When
        Optional<Address> objectFound = addressDao.read(pk);

        // Then
        assertTrue("The system cannot read the object from DB", objectFound.isPresent());
    }

    private String createSampleAddressInDb(){

            Address a1 = new Address();
            a1.setCity("city");
            a1.setCountry("country");
            a1.setHousenumber("2a");
            a1.setState("state");
            a1.setStreet("street");
            a1.setZipcode("1964");

            return addressDao.create(a1);
    }
}
