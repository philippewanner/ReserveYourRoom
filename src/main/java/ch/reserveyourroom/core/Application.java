package ch.reserveyourroom.core;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ApplicationPath;

/**
 * Created by philippe.wanner on 19/07/16.
 */

@ApplicationPath("/api")
public class Application extends javax.ws.rs.core.Application {

    private final static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public Application(){

        LOGGER.info("=======================");
        LOGGER.info("Application is starting");
        LOGGER.info("=======================");
    }

}
