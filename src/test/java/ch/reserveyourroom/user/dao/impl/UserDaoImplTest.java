package ch.reserveyourroom.user.dao.impl;

import ch.reserveyourroom.reservation.dao.ReservationDao;
import ch.reserveyourroom.reservation.dao.impl.ReservationDaoImpl;
import ch.reserveyourroom.user.dao.UserDao;
import ch.reserveyourroom.user.model.User;
import ch.reserveyourroom.wish.dao.WishDao;
import ch.reserveyourroom.wish.dao.impl.WishDaoImpl;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

/**
 * Unit testing the user dao implementation.
 */

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest extends TestCase {

    private UserDao userDao = new UserDaoImpl();
    private final WishDao wishDao = new WishDaoImpl();
    private final ReservationDao reservationDao = new ReservationDaoImpl();


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
        userDao.setEntityManager(em);
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
/*
    @Test
    public void should_countAllUsers() {
        // Given
        User u1 = new User();
        u1.setEmail("u1@mail.com");
        u1.setFirstname("u");
        u1.setLastname("1");
        userDao.create(u1);

        User u2 = new User();
        u2.setEmail("u2@mail.com");
        u2.setFirstname("u");
        u2.setLastname("2");
        userDao.create(u2);

        // When
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        Predicate alwaysTrue = cb.or();
        final long count = userDao.countAll( alwaysTrue );

        // Then
        assertTrue("The number of users has to be two", 2 == count);
    }
*/
    @Test
    public void should_createANewUserInTheDb() {

        // Given
        User u1 = new User();
        u1.setEmail("u1@mail.com");
        u1.setFirstname("u");
        u1.setLastname("1");

        // When
        final String userId = userDao.create(u1);

        // Then
        User userRead = userDao.read(userId).get();
        assertTrue("The Id of the user can not be read", userId.compareTo(userRead.getUuid().toString()) == 0);
    }
}

/*


    long countAll(Predicate predicate);
    List<T> loadAll();
    String create(T t);
    void delete(T t);
    Optional<T> read(String id);
    T update(T t) throws EntityOptimisticLockException;


@RunWith(MockitoJUnitRunner.class)
public class AnamneseDaoImplTest extends DaoTestTemplate {

    private final NeurologischesUntersuchungDaoImpl neuroDao = new NeurologischesUntersuchungDaoImpl();
    private final AnamneseDao dao = new AnamneseDaoImpl(neuroDao);
    private final BehandlungsfallDao behandlungsfallDao = new BehandlungsfallDaoImpl();
    private final DiagnoseDao diagnoseDao = new DiagnoseDaoImpl();
    private final ProtokollVorlageDao protokollvorlageDao = new ProtokollVorlageDaoImpl();
    private final BereichDao bereichDao = new BereichDaoImpl();
    private final VertragspartnerDao vertragspartnerDao = new VertragspartnerDaoImpl();
    private final PraxisDao praxisDao = new PraxisDaoImpl();
    private final PatientDao patientDao = new PatientDaoImpl();
    private final DoctorDao doctorDao = new DoctorDaoImpl();
    private final KostentraegerDao kostentragerDao = new KostentraegerDaoImpl();
    private Anamnese anamneseA;
    private Anamnese anamneseB;

    @Override
    protected List<GenericDao<?>> getDaos() {

        return Arrays
                .asList(this.dao, this.behandlungsfallDao, this.diagnoseDao, this.protokollvorlageDao, this.bereichDao,
                        this.vertragspartnerDao, this.praxisDao, this.patientDao, this.doctorDao, this.kostentragerDao);
    }

    @Before
    public void before() {

        Date date = new Date();

        Bereich bereich = TestUtils.createBereich("Bereich sample", Status.ACTIVE);
        bereichDao.persist(bereich);

        Diagnose diagnose = TestUtils
                .createDiagnose("Diagnose sample", bereich, Patientengeschichte.NACH_OPERATION, true, Stadium.CHRONISCH);
        diagnoseDao.persist(diagnose);

        ProtokollVorlage protokollvorlage = TestUtils.createProtokollVorlage("Protokoll sample", Status.ACTIVE, bereich);
        protokollvorlageDao.persist(protokollvorlage);

        Address address = TestUtils.createAddress("Some city", "pobox", "state", "street", "zipcode");
        Praxis praxis = TestUtils
                .createPraxis("Praxis sample", "test@test.com", "432432432432", "432432432432", "http://www.test.ch",
                        address);
        praxisDao.persist(praxis);

        Vertragspartner vertragspartner = TestUtils.createVertragspartner("Vertragspartner A", praxis);
        vertragspartnerDao.persist(vertragspartner);

        Patient patient = TestUtils.createPatient("Frank", "Dubosc", "03333", vertragspartner);
        patientDao.persist(patient);

        Doctor doctor = TestUtils.createDoctor("Jean", "Charles");
        doctorDao.persist(doctor);

        Kostentraeger kostentrager = TestUtils.createKostentraeger("Kostentrage sample");
        kostentragerDao.persist(kostentrager);

        Behandlungsfall behandlungsfallA = TestUtils
                .createBehandlungsfall("BusinesIDA", date, diagnose, protokollvorlage, praxis, patient, doctor,
                        kostentrager);

        Behandlungsfall behandlungsfallB = TestUtils
                .createBehandlungsfall("BusinesIDB", date, diagnose, protokollvorlage, praxis, patient, doctor,
                        kostentrager);

        behandlungsfallDao.persist(behandlungsfallA);
        behandlungsfallDao.persist(behandlungsfallB);

        // A
        this.anamneseA = new Anamnese();
        this.anamneseA.setCognitiveFactors(this.randomString(100));
        this.anamneseA.setComorbidities(this.randomString(100));
        this.anamneseA.setComplaints(this.randomString(100));
        this.anamneseA.setComplaintsDevelopment(this.randomString(100));
        this.anamneseA.setFunctionalLimitations(this.randomString(100));
        this.anamneseA.setLifestyleFactors(this.randomString(100));
        this.anamneseA.setMriDiagnosis(this.randomString(100));
        this.anamneseA.setPatientGoal(this.randomString(100));
        this.anamneseA.setAerztlicheInformationen(this.randomString(100));
        this.anamneseA.setStage(Stadium.REZIDIVIEREND);
        this.anamneseA.setVas24h((short) 0);
        this.anamneseA.setSchmerzzoneP1(this.randomString(50));
        this.anamneseA.setSchmerzzoneP2(this.randomString(50));
        this.anamneseA.setSchmerzzoneP3(this.randomString(50));
        this.anamneseA.setPainCharacter24h(PainCharacter.STECHEND);
        this.anamneseA.setStatus(Status.ACTIVE);
        this.anamneseA.setPatientengeschichte(Patientengeschichte.KRANKHEIT);
        this.anamneseA.setBehandlungsfall(behandlungsfallA);
        this.anamneseA.setBelastbarkeit(Belastbarkeit.OPTIMAL);
        this.anamneseA.setBelastbarkeitComment(this.randomString(150));
        this.anamneseA.setBelastbarkeitSkala((short) 5);
        this.anamneseA.setEmotionaleFaktoren(EmotionaleFaktoren.FRUSTRATION);
        this.anamneseA.setEmotionaleFaktorenComment(this.randomString(150));
        this.dao.persist(this.anamneseA);

        // B
        this.anamneseB = new Anamnese();
        this.anamneseB.setCognitiveFactors(null);
        this.anamneseB.setComorbidities(null);
        this.anamneseB.setComplaints(null);
        this.anamneseB.setComplaintsDevelopment(null);
        this.anamneseB.setFunctionalLimitations(null);
        this.anamneseB.setLifestyleFactors(null);
        this.anamneseB.setMriDiagnosis(null);
        this.anamneseB.setPatientGoal(null);
        this.anamneseB.setAerztlicheInformationen(null);
        this.anamneseB.setStage(Stadium.AKUT);
        this.anamneseB.setVasMittag((short) 10);
        this.anamneseB.setSchmerzzoneP1(null);
        this.anamneseB.setSchmerzzoneP2(null);
        this.anamneseB.setSchmerzzoneP3(null);
        this.anamneseB.setPainCharacterMittag(null);
        this.anamneseB.setStatus(Status.INACTIVE);
        this.anamneseB.setPatientengeschichte(Patientengeschichte.NACH_OPERATION);
        this.anamneseB.setBehandlungsfall(behandlungsfallB);
        this.anamneseB.setBelastbarkeit(Belastbarkeit.LEICHT_ERNIEDRIGT);
        this.anamneseB.setBelastbarkeitComment(this.randomString(150));
        this.anamneseB.setBelastbarkeitSkala((short) 3);
        this.anamneseB.setEmotionaleFaktoren(EmotionaleFaktoren.DEPRESSION);
        this.anamneseB.setEmotionaleFaktorenComment(this.randomString(150));
        this.dao.persist(this.anamneseB);

        this.entityManager.flush();
    }

    @Test
    public void testFindAll() {

        List<Anamnese> result = this.dao.findAll();
        assertNotNull(result);
        assertEquals(Sets.newHashSet(this.anamneseA, this.anamneseB), new HashSet<>(result));
    }

    @Test
    public void testUpdate() throws PhysioQMapOptimisticLockException {

        Short vas = 5;
        String patientGoal = "<Patien goal>";

        List<Anamnese> result = this.dao.findAll();
        Anamnese anamnese = result.get(0);
        anamnese.setStatus(Status.INACTIVE);
        anamnese.setVasAbend(vas);
        anamnese.setPatientGoal(patientGoal);
        this.dao.update(anamnese);

        flushAndClear();

        Optional<Anamnese> found = this.dao.find(anamnese.getId());
        assertEquals(Status.INACTIVE, found.get().getStatus());
        assertEquals(vas, found.get().getVasAbend());
        assertEquals(patientGoal, found.get().getPatientGoal());
    }

    @Test
    public void testDelete() {

        List<Anamnese> result = this.dao.findAll();
        Anamnese anamnese = result.get(0);
        this.dao.delete(anamnese.getId());

        flushAndClear();

        Optional<Anamnese> found = this.dao.find(anamnese.getId());
        assertFalse(found.isPresent());
    }

    private String randomString(int targetStringLength) {

        return RandomStringUtils.randomAlphanumeric(targetStringLength);

    }

}
*/