package ch.reserveyourroom.core.data;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.building.service.BuildingService;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.common.exception.persistence.PersistenceException;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
import ch.reserveyourroom.room.dao.RoomDao;
import ch.reserveyourroom.room.model.Room;
import ch.reserveyourroom.room.service.RoomService;
import ch.reserveyourroom.user.model.User;
import ch.reserveyourroom.user.service.UserService;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.*;

/**
 * Generator of sample data and save into the database.
 */

@Singleton
@Startup
public class SampleDataGenerator {

    @Inject
    private Logger logger;

    @Inject
    private UserService userService;

    @Inject
    private BuildingService buildingService;

    @Inject
    private BuildingDao buildingDao;

    @Inject
    private RoomService roomService;

    @Inject
    private RoomDao roomDao;

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
            logger.debug("create users");
            createUsers();
            logger.debug("create building, with address, rooms");
            createBuildings();

        } catch (Exception e) {
            logger.error("Error when creating the sample data at start-up", e);
        }
    }

    private void createUsers() throws PersistenceException {

        for (int i = 0; i < 100; i++) {

            //Fake test data as a person
            Fairy fairy = Fairy.create();
            Person person = fairy.person();

            // User entity object to persist
            User user = new User();
            user.setFirstname(person.firstName());
            user.setLastname(person.lastName());
            user.setEmail(person.companyEmail());
            userService.save(user);
        }
    }

    private void createBuildings() {

        for (int i = 0; i<50; i++){
            //Fake test data as a room
            Fairy fairy = Fairy.create();

            // Building address
            Address addressBuilding = new Address();
            addressBuilding.setCity(fairy.person().getAddress().getCity());
            addressBuilding.setCountry("Switzerland");
            addressBuilding.setHousenumber(""+i%12+1);
            addressBuilding.setState("");
            addressBuilding.setStreet(fairy.person().getAddress().street());
            addressBuilding.setZipcode(fairy.person().getAddress().getPostalCode());

            // Building setters
            Building building = new Building();
            building.setAddress(addressBuilding);
            building.setName(fairy.company().name());

            // Building rooms
            Set<Room> rooms = new TreeSet<>();
            for(int j=0; j<10; j++){
                Room room = new Room();
                room.setFloor(i%4+j);
                room.setName("B"+i+"-Room"+j);
                room.setSeatnumber(j%5+14+i);
                room.setSize((float)(40+i*1.2+j*0.2));
                room.setBuilding(building);

                Infrastructure infrastructure = new Infrastructure();
                infrastructure.setName("infra-"+i+j);
                Set<Infrastructure> infrastructures = new TreeSet<>();
                infrastructures.add(infrastructure);
                infrastructure.setRoom(room);
                room.setInfrastructures(infrastructures);

                rooms.add(room);
            }

            building.setRooms(rooms);
            buildingService.save(building);
        }
    }

    /*
    private void createBuildings() {

        for (int i = 0; i<50; i++){
            //Fake test data as a room
            Fairy fairy = Fairy.create();

            // Building address
            Address addressBuilding = new Address();
            addressBuilding.setCity(fairy.person().getAddress().getCity());
            addressBuilding.setCountry("Switzerland");
            addressBuilding.setHousenumber(""+i%12+1);
            addressBuilding.setState("");
            addressBuilding.setStreet(fairy.person().getAddress().street());
            addressBuilding.setZipcode(fairy.person().getAddress().getPostalCode());

            // Building rooms
            Set<Room> rooms = new TreeSet<>();
            for(int j=0; j<10; j++){
                Room room = new Room();
                room.setFloor(i%4+j);
                room.setName("B"+i+"-Room"+j);
                room.setSeatnumber(j%5+14+i);
                room.setSize((float)(40+i*1.2+j*0.2));
                rooms.add(room);
            }

            // Building setters
            Building building = new Building();
            building.setAddress(addressBuilding);
            building.setName(fairy.company().name());
            buildingDao.create(building);

            building.setRooms(rooms);
            for(Room room: rooms) {
                roomDao.create(room);
            }

        }
    }*/
}
