package ch.reserveyourroom.core;


import ch.reserveyourroom.common.endpoint.Routes;
import ch.reserveyourroom.core.data.SampleDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

/**
 * Application class that will be launch at first, at start-up.
 */

@ApplicationPath(Routes.API)
public class Application extends javax.ws.rs.core.Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public Application(){

        LOGGER.info("=======================");
        LOGGER.info("Application is starting");
        LOGGER.info("=======================");

        // Fill the database with sample data
        LOGGER.info("Generating test data...");
        SampleDataGenerator.getInstance().execute();

        LOGGER.info("=======================");
        LOGGER.info("Application has started");
        LOGGER.info("=======================");
    }

}
