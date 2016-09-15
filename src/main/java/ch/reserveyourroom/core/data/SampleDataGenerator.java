package ch.reserveyourroom.core.data;

import ch.reserveyourroom.address.model.Address;
import ch.reserveyourroom.address.service.AddressService;
import ch.reserveyourroom.building.dao.BuildingDao;
import ch.reserveyourroom.building.model.Building;
import ch.reserveyourroom.building.service.BuildingService;
import ch.reserveyourroom.common.exception.persistence.EntityOptimisticLockException;
import ch.reserveyourroom.common.exception.persistence.PersistenceException;
import ch.reserveyourroom.infrastructure.model.Infrastructure;
import ch.reserveyourroom.infrastructure.service.InfrastructureService;
import ch.reserveyourroom.reservation.model.Reservation;
import ch.reserveyourroom.reservation.service.ReservationService;
import ch.reserveyourroom.room.dao.RoomDao;
import ch.reserveyourroom.room.model.Room;
import ch.reserveyourroom.room.service.RoomService;
import ch.reserveyourroom.user.model.User;
import ch.reserveyourroom.user.service.UserService;
import ch.reserveyourroom.wish.model.Wish;
import ch.reserveyourroom.wish.service.WishService;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Month;
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
    private AddressService addressService;

    @Inject
    private UserService userService;

    @Inject
    private BuildingService buildingService;

    @Inject
    private RoomService roomService;

    @Inject
    private WishService wishService;

    @Inject
    private ReservationService reservationService;

    @Inject
    private InfrastructureService infrastructureService;

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

            createSampeData();

        } catch (Exception e) {
            logger.error("Error when creating the sample data at start-up", e);
        }
    }

    private void createSampeData() {

        for (int i = 0; i<100; i++){
            //Fake test data as a room
            Fairy fairy = Fairy.create();

            // Building address
            Address address = new Address();
            address.setCity(fairy.person().getAddress().getCity());
            address.setCountry("Switzerland");
            address.setHousenumber(""+i%12+1);
            address.setState("");
            address.setStreet(fairy.person().getAddress().street());
            address.setZipcode(fairy.person().getAddress().getPostalCode());
            UUID addressId = addressService.save(address);


            // Building setters
            Building building = new Building();
            building.setAddressId(addressId);
            building.setName(fairy.company().name());
            UUID buildingId = buildingService.save(building);

            // Building rooms
            for(int j=0; j<fairy.baseProducer().randomBetween(4, 30); j++){
                Room room = new Room();
                room.setFloor(i%4+j);
                room.setName("Building-"+i+"-Room-"+j);
                room.setSeatnumber(j%5+14+i);
                room.setSize((float)(40+i*1.2+j*0.2));
                room.setBuildingId(buildingId);
                UUID roomId = roomService.save(room);

                for(int k=0; k<fairy.baseProducer().randomBetween(1, 10); k++) {
                    Infrastructure infrastructure = new Infrastructure();
                    infrastructure.setName("addr" + i + "room" + j + "infra" + k);
                    infrastructure.setRoomId(roomId);
                    infrastructureService.save(infrastructure);
                }

                for(int n=0; n<fairy.baseProducer().randomBetween(2, 14); n++) {
                    User user = new User();
                    Person person = fairy.person();
                    user.setFirstname(person.firstName());
                    user.setEmail(person.companyEmail());
                    user.setLastname(person.lastName());
                    UUID userId = userService.save(user);

                    for(int m=0; m<fairy.baseProducer().randomBetween(1, 3); m++) {
                        Wish wish = new Wish();
                        wish.setRoomId(roomId);
                        wish.setUserId(userId);
                        wish.setStart(new Date(1, 1, 1948));
                        wish.setEnd(new Date(2, 2, 1956));
                        wishService.save(wish);
                    }

                    for(int r=0; r<fairy.baseProducer().randomBetween(1, 6); r++){
                        Reservation reservation = new Reservation();
                        reservation.setRoomId(roomId);
                        reservation.setUserId(userId);
                        reservation.setStart(new Date(3, 3, 2000));
                        reservation.setEnd(new Date(4, 4, 2010));
                        reservationService.save(reservation);


                    }
                }
            }

            //buildingService.save(building);
        }
    }
}
