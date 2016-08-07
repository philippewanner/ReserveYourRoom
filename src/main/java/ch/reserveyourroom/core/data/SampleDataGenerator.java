package ch.reserveyourroom.core.data;

import ch.reserveyourroom.common.exception.persistence.PersistenceException;
import ch.reserveyourroom.common.exception.persistence.EntityUnprocessableException;
import ch.reserveyourroom.user.model.User;
import ch.reserveyourroom.user.service.impl.UserServiceImpl;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Generator of sample data and save into the database.
 */

@Singleton
@Startup
public class SampleDataGenerator {

    private final static Logger LOGGER = LoggerFactory.getLogger(SampleDataGenerator.class);

    @Inject
    private UserServiceImpl userServiceImpl;

    private static SampleDataGenerator instance;


    @PostConstruct
    public void initialize() {

        SampleDataGenerator.instance = this;
    }

    public static SampleDataGenerator getInstance() {

        return SampleDataGenerator.instance;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void execute() {

        try {
            // The order mights matter!
            createUsers();

        } catch (Exception e) {
            LOGGER.error("Error when creating the sample data at start-up", e);
        }
    }

    private void createUsers() throws PersistenceException {

        for (int i = 0; i < 1000; i++) {

            //Fake test data as a person
            Fairy fairy = Fairy.create();
            Person person = fairy.person();

            // User entity object to persist
            User user = new User();
            user.setFirstname(person.firstName());
            user.setLastname(person.lastName());
            user.setEmail(person.companyEmail());
            userServiceImpl.save(user);
        }
    }
}
