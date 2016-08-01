package ch.reserveyourroom.core;


import ch.reserveyourroom.common.endpoint.Route;
import ch.reserveyourroom.core.data.TestDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

/**
 * Application class that will be launch at first, at start-up.
 */

@ApplicationPath(Route.API)
public class Application extends javax.ws.rs.core.Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public Application(){

        LOGGER.info("=======================");
        LOGGER.info("Application is starting");
        LOGGER.info("=======================");

        // Fill the database with sample data
        LOGGER.info("Generating test data...");
        TestDataGenerator.getInstance().execute();

        LOGGER.info("=======================");
        LOGGER.info("Application has started");
        LOGGER.info("=======================");
    }

}
