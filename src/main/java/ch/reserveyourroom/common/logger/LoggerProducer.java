package ch.reserveyourroom.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Auto injects the underlying implementation of slf4j logger.
 */
@Dependent
public class LoggerProducer {

    @Produces
    public Logger producer(InjectionPoint ip){
        return LoggerFactory.getLogger(
                ip.getMember().getDeclaringClass().getName());
    }
}